package com.androidatlas.notes.feature.noteslist;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\tR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0017"}, d2 = {"Lcom/androidatlas/notes/feature/noteslist/NotesListViewModel;", "Landroidx/lifecycle/ViewModel;", "noteRepository", "Lcom/androidatlas/notes/core/database/repository/NoteRepository;", "syncQueueProcessor", "Lcom/androidatlas/notes/core/sync/processor/SyncQueueProcessor;", "(Lcom/androidatlas/notes/core/database/repository/NoteRepository;Lcom/androidatlas/notes/core/sync/processor/SyncQueueProcessor;)V", "_searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "searchQuery", "Lkotlinx/coroutines/flow/StateFlow;", "getSearchQuery", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "Lkotlinx/coroutines/flow/Flow;", "Lcom/androidatlas/notes/feature/noteslist/NotesListUiState;", "getUiState", "()Lkotlinx/coroutines/flow/Flow;", "clearSearchQuery", "", "updateSearchQuery", "query", "noteslist_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class NotesListViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.androidatlas.notes.core.database.repository.NoteRepository noteRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.androidatlas.notes.core.sync.processor.SyncQueueProcessor syncQueueProcessor = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.androidatlas.notes.feature.noteslist.NotesListUiState> uiState = null;
    
    @javax.inject.Inject()
    public NotesListViewModel(@org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.core.database.repository.NoteRepository noteRepository, @org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.core.sync.processor.SyncQueueProcessor syncQueueProcessor) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.androidatlas.notes.feature.noteslist.NotesListUiState> getUiState() {
        return null;
    }
    
    public final void updateSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void clearSearchQuery() {
    }
}