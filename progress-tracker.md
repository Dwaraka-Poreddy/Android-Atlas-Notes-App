---
name: build-progress-comprehensive
description: Complete project structure, progress tracker, and file inventory for Android Atlas Notes app
sources: [chat]
---

# Android Atlas Notes App — Comprehensive Progress Tracker

**Project Name:** AndroidAtlas-Notes  
**Package:** com.androidatlas.notes  
**Min SDK:** 26  
**Compile SDK:** 34  
**Language:** Kotlin (DSL)  
**UI Framework:** Jetpack Compose  
**Architecture:** MVVM + Offline-First Sync  
**Backend:** FastAPI (local at 192.168.1.4:8000 for physical device)

---

## Project Structure Overview

```
AndroidAtlas-Notes/
├── gradle/
│   └── libs.versions.toml                    ✅ COMPLETE
├── core/
│   ├── common/
│   │   ├── build.gradle.kts                  ✅ COMPLETE
│   │   └── src/main/kotlin/com/androidatlas/notes/core/common/
│   │       ├── model/
│   │       │   ├── Note.kt                   ✅ COMPLETE
│   │       │   ├── Result.kt                 ✅ COMPLETE
│   │       │   ├── SyncOperation.kt          ✅ COMPLETE
│   │       │   ├── OperationType.kt          ✅ COMPLETE
│   │       │   └── SyncQueueStatus.kt        ✅ COMPLETE
│   │
│   ├── database/
│   │   ├── build.gradle.kts                  ✅ COMPLETE
│   │   └── src/main/kotlin/com/androidatlas/notes/core/database/
│   │       ├── entity/
│   │       │   ├── SyncStatus.kt             ✅ COMPLETE
│   │       │   ├── NoteEntity.kt             ✅ COMPLETE
│   │       │   ├── SyncOperationEntity.kt    ✅ COMPLETE
│   │       │   └── NoteFts.kt                ✅ COMPLETE
│   │       ├── converter/
│   │       │   └── SyncStatusConverter.kt    ✅ COMPLETE
│   │       ├── dao/
│   │       │   ├── NoteDao.kt                ✅ COMPLETE
│   │       │   └── SyncOperationDao.kt       ✅ COMPLETE
│   │       ├── db/
│   │       │   └── AppDatabase.kt            ✅ COMPLETE
│   │       ├── mapper/
│   │       │   └── NoteEntityMapper.kt       ✅ COMPLETE
│   │       └── repository/
│   │           └── NoteRepository.kt         ✅ COMPLETE
│   │
│   ├── network/
│   │   ├── build.gradle.kts                  ✅ COMPLETE
│   │   └── src/main/kotlin/com/androidatlas/notes/core/network/
│   │       ├── dto/
│   │       │   ├── NoteDto.kt                ✅ COMPLETE
│   │       │   ├── AuthTokensDto.kt          ✅ COMPLETE
│   │       │   ├── SyncRequestDto.kt         ✅ COMPLETE
│   │       │   ├── SyncResponseDto.kt        ✅ COMPLETE
│   │       │   └── AuthDto.kt                ✅ COMPLETE
│   │       ├── api/
│   │       │   ├── AuthApiService.kt         ✅ COMPLETE
│   │       │   └── NotesApiService.kt        ✅ COMPLETE
│   │       ├── interceptor/
│   │       │   ├── TokenManager.kt           ✅ COMPLETE
│   │       │   └── AuthInterceptor.kt        ✅ COMPLETE
│   │       └── retrofit/
│   │           └── RetrofitClient.kt         ✅ COMPLETE
│   │
│   ├── sync/
│   │   ├── build.gradle.kts                  ✅ COMPLETE
│   │   └── src/main/kotlin/com/androidatlas/notes/core/sync/
│   │       ├── backoff/
│   │       │   └── BackoffPolicy.kt          ✅ COMPLETE
│   │       ├── processor/
│   │       │   └── SyncQueueProcessor.kt     ✅ COMPLETE
│   │       └── worker/
│   │           ├── SyncWorker.kt             ✅ COMPLETE
│   │           └── SyncScheduler.kt          ✅ COMPLETE
│   │
│   ├── designsystem/
│   │   ├── build.gradle.kts                  ✅ COMPLETE
│   │   └── src/main/kotlin/com/androidatlas/notes/core/designsystem/
│   │       ├── theme/
│   │       │   ├── Color.kt                  ✅ COMPLETE
│   │       │   ├── Typography.kt             ✅ COMPLETE
│   │       │   └── Theme.kt                  ✅ COMPLETE
│   │       └── component/
│   │           ├── Button.kt                 ✅ COMPLETE
│   │           ├── TextField.kt              ✅ COMPLETE
│   │           ├── Card.kt                   ✅ COMPLETE
│   │           └── SyncIndicator.kt          ✅ COMPLETE
│   │
│   └── navigation/
│       ├── build.gradle.kts                  ✅ COMPLETE
│       └── src/main/kotlin/com/androidatlas/notes/core/navigation/
│           ├── NavigationRoute.kt            ✅ COMPLETE
│           └── NavigationUtils.kt            ✅ COMPLETE
│
├── feature/
│   ├── notes-list/
│   │   ├── build.gradle.kts                  ✅ COMPLETE
│   │   └── src/main/kotlin/com/androidatlas/notes/feature/noteslist/
│   │       ├── NotesListViewModel.kt         ✅ COMPLETE
│   │       └── NotesListScreen.kt            ✅ COMPLETE
│   │
│   └── note-editor/
│       ├── build.gradle.kts                  ✅ COMPLETE
│       └── src/main/kotlin/com/androidatlas/notes/feature/noteeditor/
│           ├── SaveNoteUseCase.kt            ⏳ PENDING
│           ├── DeleteNoteUseCase.kt          ⏳ PENDING
│           ├── NoteEditorViewModel.kt        ⏳ PENDING
│           └── NoteEditorScreen.kt           ⏳ PENDING
│
├── app/
│   ├── build.gradle.kts                      ✅ COMPLETE
│   ├── src/main/kotlin/com/androidatlas/notes/
│   │   ├── AndroidAtlasApp.kt                ⏳ PENDING (Hilt @HiltAndroidApp)
│   │   ├── MainActivity.kt                   ⏳ PENDING
│   │   └── di/
│   │       └── AppModule.kt                  ⏳ PENDING (Hilt module)
│   └── src/main/AndroidManifest.xml          ⏳ PENDING
│
├── settings.gradle.kts                       ✅ COMPLETE
└── build.gradle.kts                          ✅ COMPLETE
```

---

## Milestone Status

### ✅ Milestone 1: Project Skeleton + Core Data Layer — COMPLETE

**What was built:**
- Project structure with 8 modules (core + feature + app)
- Gradle configuration (settings.gradle.kts, root build.gradle.kts, libs.versions.toml)
- All module build.gradle.kts files with correct dependencies

**Files created:** 11 build configuration files

---

### ✅ Milestone 2: Core Data & Network Layer — COMPLETE

#### `:core:common` — ✅ COMPLETE (5 files)
- **Purpose:** Domain models, shared types (no dependencies on other modules)
- **Files:**
  - `Note.kt` — domain model (id, title, content, folderOrLabel, updatedAt)
  - `Result.kt` — sealed class (Success<T>, Error, Loading)
  - `SyncOperation.kt` — client sync operation with localId, operationType, noteId, clientUpdatedAt
  - `OperationType.kt` — enum (UPSERT, DELETE)
  - `SyncQueueStatus.kt` — UI sync state (pendingCount, isSyncing, lastSyncedAt)

#### `:core:database` — ✅ COMPLETE (10 files)
- **Purpose:** Room persistence, DAOs, database setup, repository for reads
- **Files:**
  - `entity/SyncStatus.kt` — enum (PENDING, SYNCING, FAILED, SYNCED)
  - `entity/NoteEntity.kt` — DB entity with syncStatus field (NOT in domain model)
  - `entity/SyncOperationEntity.kt` — queue entry (id, localId, operationType, noteId, status)
  - `entity/NoteFts.kt` — @Fts4 virtual table for full-text search
  - `converter/SyncStatusConverter.kt` — TypeConverter (enum ↔ String)
  - `dao/NoteDao.kt` — queries: getAllNotes, getNoteById, searchNotes (MATCH), insertOrUpdateNote, softDeleteNote
  - `dao/SyncOperationDao.kt` — queries: getPendingOperations, observePendingCount, enqueueOperation, deleteOperation, updateOperationStatus, deletePendingOperationsForNote
  - `db/AppDatabase.kt` — Room database setup with TypeConverters, singleton getInstance()
  - `mapper/NoteEntityMapper.kt` — toDomain (entity→Note, strips syncStatus), toEntity (Note→entity)
  - `repository/NoteRepository.kt` — data layer (read only): getAllNotes(), getNote(id), searchNotes(query), saveNote(), deleteNote()

#### `:core:network` — ✅ COMPLETE (9 files)
- **Purpose:** Retrofit API contracts, auth interceptor, token management
- **Files:**
  - `dto/NoteDto.kt` — server Note shape (id, userId, title, content, folderOrLabel, updatedAt, deleted)
  - `dto/AuthTokensDto.kt` — {accessToken, refreshToken}
  - `dto/SyncRequestDto.kt` — {operations: [SyncOperationDto]}
  - `dto/SyncResponseDto.kt` — {results: [SyncResultDto]} (localId, status, note, message)
  - `dto/AuthDto.kt` — RegisterRequestDto, LoginRequestDto, RefreshTokenRequestDto, LogoutRequestDto, UserDto
  - `api/AuthApiService.kt` — Retrofit interface (register, login, refresh, logout, getMe)
  - `api/NotesApiService.kt` — Retrofit interface (listNotes, getNote, sync) + NotesListResponseDto
  - `interceptor/TokenManager.kt` — in-memory token storage (accessToken, refreshToken)
  - `interceptor/AuthInterceptor.kt` — attach token header, handle 401 → silent refresh → retry
  - `retrofit/RetrofitClient.kt` — singleton Retrofit setup (base URL: 192.168.1.4:8000, OkHttp, Gson, auth interceptor)

#### `:core:sync` — ✅ COMPLETE (4 files)
- **Purpose:** Offline sync engine, retry logic, WorkManager integration
- **Files:**
  - `backoff/BackoffPolicy.kt` — exponential backoff (1s → 2s → 4s → 8s → 16s → 30s max) + jitter ±10%, max 5 retries
  - `processor/SyncQueueProcessor.kt` — enqueue (with collapsing), processPendingOperations (handles applied/conflict/error), observeQueueStatus
  - `worker/SyncWorker.kt` — WorkManager CoroutineWorker, calls processPendingOperations
  - `worker/SyncScheduler.kt` — helper to enqueueUniqueWork("sync_notes") with network constraint

**Key design for Milestone 2:**
- Single source of truth: local Room database
- Push-based sync on save/delete, with persisted retry queue
- Last-write-wins conflict resolution (server's updatedAt wins)
- Operation collapsing: same note edited twice offline → delete old op, enqueue new one
- Auth interceptor transparently handles 401 → refresh → retry

---

### ✅ Milestone 3: Design System & Navigation — COMPLETE

#### `:core:designsystem` — ✅ COMPLETE (7 files)
- **Purpose:** Compose theme, colors, typography, reusable components
- **Files:**
  - `theme/Color.kt` — AtlasColors (primary blue, secondary red, neutral grays, semantic)
  - `theme/Typography.kt` — AtlasTypography (display, headline, body, label scales)
  - `theme/Theme.kt` — AtlasTheme composable, ColorScheme setup
  - `component/Button.kt` — AtlasPrimaryButton, AtlasSecondaryButton, AtlasOutlinedButton (48.dp height, rounded corners)
  - `component/TextField.kt` — AtlasTextField (single/multi-line, error states)
  - `component/Card.kt` — AtlasNoteCard (displays note title, content snippet, label, clickable)
  - `component/SyncIndicator.kt` — AtlasSyncIndicator (shows "Syncing...", "X changes pending", or hides)

#### `:core:navigation` — ✅ COMPLETE (2 files)
- **Purpose:** Route definitions, navigation helpers (string-based, no DI Navigator)
- **Files:**
  - `NavigationRoute.kt` — sealed class (NotesList, NoteEditor(noteId?))
  - `NavigationUtils.kt` — navigateToNotesList(), navigateToNoteEditor(noteId?), extractNoteId()

---

### 🔄 Milestone 4: Feature — Notes List — IN PROGRESS

#### `:feature:notes-list` — 🔄 IN PROGRESS (2/2 files)
- **Purpose:** Browse and search notes, display sync status
- **Files:**
  - ✅ `NotesListViewModel.kt` — observes Flow<NotesListUiState> (combines search query + allNotes + syncStatus), filterBy search using Repository.searchNotes() (FTS), updateSearchQuery()
  - ✅ `NotesListScreen.kt` — Compose UI: search bar (AtlasTextField), sync indicator (AtlasSyncIndicator), LazyColumn of AtlasNoteCard, FAB to create new note, empty state

**Next step:** Move to Milestone 5 (Note Editor feature)

---

### ⏳ Milestone 5: Feature — Note Editor — PENDING

#### `:feature:note-editor` — ⏳ PENDING (4 files)
- **Purpose:** Create and edit individual notes, save/delete with sync
- **Files to create:**
  - `SaveNoteUseCase.kt` — write to NoteRepository + enqueue SyncOperation (UPSERT)
  - `DeleteNoteUseCase.kt` — soft-delete via NoteRepository + enqueue SyncOperation (DELETE)
  - `NoteEditorViewModel.kt` — observes Note by id, manages title/content/label state, calls usecases on save/delete
  - `NoteEditorScreen.kt` — Compose UI: title & content text fields, save/delete buttons, show confirmation on success

---

### ⏳ Milestone 6: App Wiring & End-to-End Test — PENDING

#### `:app` — ⏳ PENDING (3 files)
- **Purpose:** Wire all modules together with Hilt DI, nav graph, MainActivity
- **Files to create:**
  - `AndroidAtlasApp.kt` — @HiltAndroidApp application class
  - `MainActivity.kt` — Compose UI with NavHost, nav graph assembly (NotesList → NoteEditor)
  - `di/AppModule.kt` — Hilt @Module: provide AppDatabase, NoteRepository, NotesApiService, AuthApiService, SyncQueueProcessor, SyncScheduler, etc.
  - `AndroidManifest.xml` — app name, internet permission, notification permission

**End-to-end test checklist:**
- [ ] Register a new account via `/auth/register`
- [ ] Create a note offline (save locally)
- [ ] Verify note appears in list immediately (Room read)
- [ ] Go online → confirm sync to server
- [ ] Force conflict via `/debug/simulateConflict/notes/{id}` → make local edit
- [ ] Verify conflict resolution (server wins, local note overwritten)
- [ ] Final state matches server

---

## Architecture Summary

### Module Dependency Graph

```
:app
 ├── :feature:notes-list ──────┐
 ├── :feature:note-editor ─────┤
 │                             ├──> :core:database
 │                             ├──> :core:network
 │                             ├──> :core:sync
 │                             ├──> :core:designsystem
 │                             ├──> :core:common
 │                             └──> :core:navigation
```

### Data Flow

1. **User creates/edits note** in NoteEditorScreen
2. **SaveNoteUseCase** writes to NoteRepository (local Room insert/update)
3. NoteRepository **enqueues SyncOperation** to SyncQueueProcessor
4. SyncQueueProcessor adds to sync queue (with collapsing if same note edited twice)
5. **SyncWorker** (triggered by WorkManager on network availability) calls processPendingOperations
6. Sends batch to `/notes/sync` endpoint
7. **Handles responses:**
  - `"applied"` → local note updated with server version, operation deleted
  - `"conflict"` → server version overwrites local, operation deleted (last-write-wins)
  - `"error"` → retry with backoff, or mark FAILED after max retries
8. **UI observes** NoteRepository.getAllNotes() Flow → list updates immediately on local change
9. **UI observes** SyncQueueProcessor.observeQueueStatus() → shows "Syncing..." or "X pending"

### Key Design Decisions

- **MVVM** — ViewModels observe Flows, call UseCases/Repositories
- **Single source of truth** — local Room database
- **Push-based sync** — immediate on save/delete, not periodic
- **Offline-first** — all writes go local first, sync in background
- **Last-write-wins** — server's `updatedAt` timestamp enforces, client overwrites on conflict
- **TypeConverters** — SyncStatus enum ↔ String for database storage
- **FTS4** — full-text search on title + content via virtual table
- **Auth interceptor** — 401 triggers silent refresh → retry transparently (no UI interruption)
- **WorkManager** — background sync constrained to network connectivity
- **No shared Android code** — each app is standalone; only backend is shared

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| UI | Jetpack Compose |
| Architecture | MVVM + Offline-First |
| Local DB | Room + FTS4 |
| Networking | Retrofit + OkHttp |
| Async | Kotlin Coroutines + Flow |
| DI (planned) | Hilt |
| Background work | WorkManager |
| Navigation | Jetpack Compose Navigation |

---

## Progress Summary

| Milestone | Status | Files |
|-----------|--------|-------|
| 1. Project Skeleton | ✅ COMPLETE | 11 |
| 2. Core Data & Network | ✅ COMPLETE | 28 |
| 3. Design System & Navigation | ✅ COMPLETE | 9 |
| 4. Feature — Notes List | 🔄 IN PROGRESS | 2/2 |
| 5. Feature — Note Editor | ⏳ PENDING | 0/4 |
| 6. App Wiring & E2E Test | ⏳ PENDING | 0/3 |
| **TOTAL** | **50% COMPLETE** | **52/57** |

---

## Next Steps

1. ✅ **Milestone 4 COMPLETE** — NotesListViewModel + NotesListScreen done
2. ⏳ **Milestone 5** — Build SaveNoteUseCase, DeleteNoteUseCase, NoteEditorViewModel, NoteEditorScreen
3. ⏳ **Milestone 6** — Hilt setup, MainActivity, nav graph, end-to-end testing

---

## Useful Server Debug Endpoints

- `GET /debug/slow` — 5-second delay (test loading states)
- `GET /debug/error` — Always 500 (test error handling)
- `GET /debug/offline` — Always 503 (test offline handling)
- `GET /debug/randomFailure` — 30% chance of 500 (test retry logic)
- `GET /debug/reset` — Reset all data (users, notes, tokens)
- `POST /debug/simulateConflict/notes/{id}` — Bump note's `updatedAt` (test conflict resolution)

---

## Notes

- Backend runs at `http://192.168.1.4:8000` for physical device (update in RetrofitClient.kt)
- Tokens stored in-memory (not encrypted; production would use DataStore/SharedPreferences)
- No pagination implemented yet (server supports it, but not used on client)
- Pull-to-refresh not yet implemented (optional, supplementary to push sync)
- 