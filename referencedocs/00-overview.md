# Android Atlas Notes — Architecture Overview

## What This App Is

An offline-first Android notes app. Notes are written and read locally from a Room database at all times. Changes are synced to a remote FastAPI backend in the background via a persistent queue. The UI never waits for the network.

---

## Module Graph

```
:app
 ├── :feature:notes-list
 ├── :feature:note-editor
 │        │
 │        ▼
 ├── :core:common         ← domain models (Note, Result, SyncOperation)
 ├── :core:database       ← Room (NoteEntity, SyncOperationEntity, DAOs, Repository)
 ├── :core:network        ← Retrofit (AuthApiService, NotesApiService, DTOs, Interceptor)
 ├── :core:sync           ← Sync engine (SyncQueueProcessor, BackoffPolicy, WorkManager)
 ├── :core:designsystem   ← Compose theme + reusable components
 └── :core:navigation     ← Route definitions + nav helpers
```

**Dependency rules:**
- Feature modules depend on core modules only (never on each other)
- `:core:sync` depends on `:core:database` + `:core:network`
- `:core:common` has no dependencies on other modules

---

## Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| UI | Jetpack Compose | Declarative UI |
| Architecture | MVVM | Separation of concerns |
| Local DB | Room + FTS4 | Offline storage + full-text search |
| Networking | Retrofit + OkHttp | REST API calls |
| Async | Kotlin Coroutines + Flow | Reactive streams |
| DI | Hilt | Dependency injection |
| Background | WorkManager | Constrained sync work |
| Navigation | Compose Navigation | Screen routing |

---

## Complete File Inventory

```
app/
├── AndroidAtlasApp.kt           @HiltAndroidApp, starts SyncScheduler
├── MainActivity.kt              NavHost, nav graph, @AndroidEntryPoint
└── di/
    └── AppModule.kt             Hilt @Module, provides all dependencies

core/common/
└── model/
    ├── Note.kt                  Domain model (no sync details)
    ├── Result.kt                Sealed class: Success<T>, Error, Loading
    ├── SyncOperation.kt         Client sync operation model
    ├── OperationType.kt         Enum: UPSERT, DELETE
    └── SyncQueueStatus.kt       UI sync state

core/database/
├── entity/
│   ├── NoteEntity.kt            Room entity (includes syncStatus)
│   ├── SyncOperationEntity.kt   Queue entry entity
│   ├── NoteFts.kt               @Fts4 virtual table
│   └── SyncStatus.kt            Enum: PENDING, SYNCING, FAILED, SYNCED
├── converter/
│   └── SyncStatusConverter.kt   TypeConverter: SyncStatus ↔ String
├── dao/
│   ├── NoteDao.kt               CRUD + FTS queries
│   └── SyncOperationDao.kt      Queue management queries
├── db/
│   └── AppDatabase.kt           Room database setup
├── mapper/
│   └── NoteEntityMapper.kt      NoteEntity ↔ Note conversion
└── repository/
    └── NoteRepository.kt        Data access layer (reads + writes)

core/network/
├── dto/
│   ├── NoteDto.kt               Server Note JSON shape
│   ├── AuthTokensDto.kt         {accessToken, refreshToken}
│   ├── SyncRequestDto.kt        POST /notes/sync request body
│   ├── SyncResponseDto.kt       POST /notes/sync response body
│   └── AuthDto.kt               Auth request/response models
├── api/
│   ├── AuthApiService.kt        Retrofit auth endpoints
│   └── NotesApiService.kt       Retrofit notes endpoints
├── interceptor/
│   ├── TokenManager.kt          In-memory token storage
│   └── AuthInterceptor.kt       Attach token + 401 refresh
└── retrofit/
    └── RetrofitClient.kt        Retrofit singleton setup

core/sync/
├── backoff/
│   └── BackoffPolicy.kt         Exponential backoff with jitter
├── processor/
│   └── SyncQueueProcessor.kt    Sync engine (enqueue, process, observe)
└── worker/
    ├── SyncWorker.kt            WorkManager CoroutineWorker
    └── SyncScheduler.kt         Enqueue unique sync work

core/designsystem/
├── theme/
│   ├── Color.kt                 AtlasColors palette
│   ├── Typography.kt            AtlasTypography scale
│   └── Theme.kt                 AtlasTheme composable
└── component/
    ├── Button.kt                AtlasPrimaryButton, AtlasSecondaryButton, AtlasOutlinedButton
    ├── TextField.kt             AtlasTextField
    ├── Card.kt                  AtlasNoteCard
    └── SyncIndicator.kt         AtlasSyncIndicator

core/navigation/
├── NavigationRoute.kt           Sealed class: NotesList, NoteEditor(noteId?)
└── NavigationUtils.kt           navigateToNotesList(), navigateToNoteEditor(noteId?)

feature/notes-list/
├── NotesListViewModel.kt        Combines notes + search + sync status flows
└── NotesListScreen.kt           Compose UI: search, list, sync indicator, FAB

feature/note-editor/
├── SaveNoteUseCase.kt           Local write + UPSERT sync enqueue
├── DeleteNoteUseCase.kt         Soft-delete + DELETE sync enqueue
├── NoteEditorViewModel.kt       Manages title/content/label state
└── NoteEditorScreen.kt          Compose UI: text fields, save/delete
```

---

## Data Models At A Glance

### `Note` (domain model — `:core:common`)
```
Note(
  id: String,           // server-assigned UUID
  title: String,
  content: String,
  folderOrLabel: String?,
  updatedAt: String     // ISO 8601
)
```

### `NoteEntity` (database — `:core:database`)
```
NoteEntity(
  id: String,
  title: String,
  content: String,
  folderOrLabel: String?,
  updatedAt: String,
  deleted: Boolean,       // soft-delete flag
  syncStatus: SyncStatus  // PENDING | SYNCING | FAILED | SYNCED
)
```

### `SyncOperationEntity` (queue — `:core:database`)
```
SyncOperationEntity(
  id: Long,               // auto-increment queue position
  localId: String,        // UUID echoed back by server
  operationType: String,  // "UPSERT" | "DELETE"
  noteId: String?,        // null only for brand-new notes (server assigns)
  title: String?,         // null for DELETE
  content: String?,       // null for DELETE
  folderOrLabel: String?,
  clientUpdatedAt: String,
  status: String          // "PENDING" | "SYNCING" | "FAILED" | "SYNCED"
)
```

### `NoteDto` (network — `:core:network`)
```
NoteDto(
  id: String,
  userId: String,
  title: String,
  content: String,
  folderOrLabel: String?,
  updatedAt: String,
  deleted: Boolean
)
```

---

## Key Design Principles

1. **Offline-first** — all reads and writes go through Room first; network is secondary
2. **Single source of truth** — Room is the only source the UI reads from
3. **Push-based sync** — sync triggered immediately on save/delete, not on a timer
4. **Last-write-wins** — server's `updatedAt` decides the winner on conflict
5. **Operation collapsing** — editing the same note twice offline = one sync operation
6. **Silent auth refresh** — 401 triggers refresh + retry without UI interruption
7. **No shared Android code** — each Atlas app (Notes, Login, etc.) is a standalone project

---

## How Data Flows (Summary)

```
User Action
    │
    ▼
Compose Screen
    │
    ▼
ViewModel
    │
    ├──► UseCase (save/delete only)
    │         │
    │         ├──► NoteRepository ──► Room (NoteEntity)
    │         │                            │
    │         │                            ▼
    │         │                    UI observes Flow<List<Note>>
    │         │
    │         └──► SyncQueueProcessor ──► Room (SyncOperationEntity)
    │                                          │
    │                                          ▼
    │                                    WorkManager (SyncWorker)
    │                                          │
    │                                          ▼
    │                                    POST /notes/sync
    │                                          │
    │                                          ▼
    │                                    Apply server response
    │                                    (update/overwrite local)
    │
    └──► NoteRepository (reads only, no UseCase needed)
              │
              ▼
         Flow<List<Note>> → ViewModel → Screen
```

---

*See individual flow docs (01 through 08) for detailed diagrams of each specific flow.*
