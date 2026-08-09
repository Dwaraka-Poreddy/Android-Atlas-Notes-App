# AndroidAtlas — Notes App

> **App 1 of 6** in the Android Atlas learning series — mastering Android system design through hands-on implementation.

The Notes app is a fully offline-first Android application that allows users to create, edit, search, and delete notes. All changes are made locally first and synced to a remote backend in the background — the UI never waits for the network.

---

## Series Overview

| # | App | Focus |
|---|-----|-------|
| 1 | **Notes** ← you are here | Offline-first sync, Room, WorkManager |
| 2 | Login/Auth | Auth flows, token persistence, session management |
| 3 | E-commerce | Pagination, cart state, complex UI |
| 4 | Chat | WebSockets, real-time updates |
| 5 | Ride-booking | Location, maps, live tracking |
| 6 | Banking | Security, biometrics, encrypted storage |

Each app is a **fully standalone Android Studio project**. The only shared component across apps is the local FastAPI backend server.

---

## Architecture

```
:app
 ├── :feature:notes-list     Browse, search, sync indicator
 ├── :feature:note-editor    Create, edit, delete
 │          │
 │          ▼
 ├── :core:common            Domain models (Note, Result, SyncOperation)
 ├── :core:database          Room persistence (entities, DAOs, repository)
 ├── :core:network           Retrofit + OkHttp + auth interceptor
 ├── :core:sync              Sync engine (queue, backoff, WorkManager)
 ├── :core:designsystem      Compose theme + reusable components
 └── :core:navigation        Route definitions
```

### Key Design Decisions

- **Offline-first** — Room is the single source of truth. The UI reads only from Room, never directly from the network.
- **Push-based sync** — sync is triggered immediately on save/delete (not on a timer), with a persisted retry queue.
- **Last-write-wins** — conflict resolution is enforced server-side by comparing `updatedAt` timestamps.
- **Operation collapsing** — if a note is edited twice offline before syncing, only the latest state is sent to the server.
- **Silent auth refresh** — a 401 response triggers a transparent token refresh and request retry with no UI interruption.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM |
| Local DB | Room + FTS4 |
| Networking | Retrofit + OkHttp |
| Async | Kotlin Coroutines + Flow |
| DI | Hilt |
| Background work | WorkManager |
| Navigation | Jetpack Compose Navigation |

---

## Features

- ✅ Create, edit, and delete notes
- ✅ Full-text search (title + content) via Room FTS4
- ✅ Folder / label organisation
- ✅ Offline-first — works fully without network
- ✅ Background sync with exponential backoff and retry
- ✅ Conflict resolution (server version wins)
- ✅ Sync status indicator ("Syncing...", "X changes pending")
- ✅ Silent token refresh on 401

---

## Project Structure

```
AndroidAtlas-Notes/
├── app/                            App entry point, Hilt setup, MainActivity
├── core/
│   ├── common/                     Domain models, Result wrapper
│   ├── database/                   Room: entities, DAOs, repository, mappers
│   ├── network/                    Retrofit: API services, DTOs, interceptor
│   ├── sync/                       Sync engine: queue processor, backoff, worker
│   ├── designsystem/               Compose theme, colors, typography, components
│   └── navigation/                 Route definitions and nav helpers
└── feature/
    ├── notes-list/                 Notes list screen + ViewModel
    └── note-editor/                Note editor screen + ViewModel + UseCases
```

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 11+
- Python 3.9+ (for the backend)
- Physical Android device or emulator (API 26+)

### 1. Start the Backend

```bash
cd backend/
pip install -r requirements.txt
uvicorn app:app --host 0.0.0.0 --port 8000 --reload
```

### 2. Configure the Base URL

Open `core/network/src/main/kotlin/com/androidatlas/notes/core/network/retrofit/RetrofitClient.kt` and update:

```kotlin
// For emulator
private const val BASE_URL = "http://10.0.2.2:8000/"

// For physical device (use your machine's LAN IP)
private const val BASE_URL = "http://192.168.1.x:8000/"
```

### 3. Build and Run

Open the project in Android Studio, wait for Gradle sync, and run on your device/emulator.

---

## Backend API

The app communicates with a local FastAPI server.

### Auth Endpoints

| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | Login existing user |
| POST | `/auth/refresh` | Refresh access token |
| POST | `/auth/logout` | Logout |
| GET | `/auth/me` | Get current user |

### Notes Endpoints

| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/notes` | List notes (supports search, pagination) |
| GET | `/notes/{id}` | Get single note |
| POST | `/notes/sync` | Batch sync (create/update/delete) |

### Debug Endpoints (Development Only)

| Endpoint | Behaviour |
|---------|-----------|
| `GET /debug/slow` | 5-second delay — test loading states |
| `GET /debug/error` | Always 500 — test error handling |
| `GET /debug/offline` | Always 503 — test offline handling |
| `GET /debug/randomFailure` | 30% chance of 500 — test retry logic |
| `GET /debug/reset` | Reset all data to initial state |
| `POST /debug/simulateConflict/notes/{id}` | Bump note's `updatedAt` — test conflict resolution |

---

## Sync Architecture

```
User saves note
      │
      ├──► Room write (immediate)
      │         └──► UI updates instantly via Flow
      │
      └──► Sync queue (SyncOperationEntity persisted in Room)
                │
                └──► WorkManager (network constrained)
                          │
                          └──► POST /notes/sync (batch)
                                    │
                                    ├── applied  → update local with server version
                                    ├── conflict → server version overwrites local
                                    └── error    → retry with exponential backoff
```

### Conflict Resolution

Last-write-wins by `updatedAt` timestamp (enforced server-side):

```
Client edit: updatedAt = T+0  ──► server already at T+5
                                        │
                                        └──► status: "conflict"
                                             server version returned
                                             local overwritten silently
```

### Backoff Policy

```
Attempt 1 →  2s + jitter
Attempt 2 →  4s + jitter
Attempt 3 →  8s + jitter
Attempt 4 → 16s + jitter
Attempt 5 → 30s + jitter (max)
Attempt 6 → FAILED (stop retrying)
```

---

## Documentation

Detailed flow diagrams and data state walkthroughs are in the `/docs` folder:

| File | Contents |
|------|---------|
| `00-overview.md` | Architecture overview, module graph, full file inventory |
| `01-app-startup.md` | App launch → Hilt → WorkManager → Room → UI |
| `02-auth-flow.md` | Register, login, silent 401 token refresh |
| `03-notes-list-flow.md` | Screen load, FTS search, navigation |
| `04-note-create-flow.md` | New note → local write → sync enqueue → UI update |
| `05-note-edit-flow.md` | Load note → edit → Room REPLACE → operation collapsing |
| `06-note-delete-flow.md` | Soft-delete → DELETE sync op → server confirmation |
| `07-background-sync.md` | WorkManager → batch sync → result handling → backoff |
| `08-offline-conflict.md` | Offline queue build-up, collapsing, conflict resolution |

> Flowcharts use [Mermaid](https://mermaid.js.org/) syntax. View rendered diagrams in VS Code (install "Markdown Preview Mermaid Support"), GitHub (renders automatically), or Obsidian/Notion.

---

## End-to-End Test Checklist

- [ ] Register a new account
- [ ] Create a note (online) — confirm it syncs to server
- [ ] Create a note (offline) — confirm it syncs when back online
- [ ] Edit a note twice offline — confirm only latest state is synced (collapsing)
- [ ] Search notes — confirm FTS returns correct results
- [ ] Use `POST /debug/simulateConflict/notes/{id}` → edit locally → confirm server version wins
- [ ] Delete a note — confirm soft-delete + server DELETE sync
- [ ] Kill app mid-sync — reopen — confirm queue resumes

---

## Known Limitations

- Tokens stored in-memory only (lost on app restart; production would use encrypted DataStore)
- No login/register UI screen (auth handled at interceptor layer; coming in App 2)
- No pagination UI (server supports it but client always fetches first page)
- No pull-to-refresh (sync is push-based; manual refresh not yet implemented)
- Conflict resolution is silent (no UI prompt; server always wins)
