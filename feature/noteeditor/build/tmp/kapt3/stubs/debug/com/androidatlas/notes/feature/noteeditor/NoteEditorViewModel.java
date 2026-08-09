package com.androidatlas.notes.feature.noteeditor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0017J\u0010\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u000fH\u0002J\u0006\u0010\u001b\u001a\u00020\u0017J\u000e\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u000fJ\u0010\u0010\u001e\u001a\u00020\u00172\b\u0010\u001f\u001a\u0004\u0018\u00010\u000fJ\u000e\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u000fR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\""}, d2 = {"Lcom/androidatlas/notes/feature/noteeditor/NoteEditorViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "noteRepository", "Lcom/androidatlas/notes/core/database/repository/NoteRepository;", "saveNoteUseCase", "Lcom/androidatlas/notes/feature/noteeditor/SaveNoteUseCase;", "deleteNoteUseCase", "Lcom/androidatlas/notes/feature/noteeditor/DeleteNoteUseCase;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/androidatlas/notes/core/database/repository/NoteRepository;Lcom/androidatlas/notes/feature/noteeditor/SaveNoteUseCase;Lcom/androidatlas/notes/feature/noteeditor/DeleteNoteUseCase;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/androidatlas/notes/feature/noteeditor/NoteEditorUiState;", "noteId", "", "getNoteId", "()Ljava/lang/String;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "deleteNote", "loadNote", "id", "saveNote", "updateContent", "content", "updateLabel", "label", "updateTitle", "title", "noteeditor_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class NoteEditorViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.androidatlas.notes.core.database.repository.NoteRepository noteRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.androidatlas.notes.feature.noteeditor.SaveNoteUseCase saveNoteUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.androidatlas.notes.feature.noteeditor.DeleteNoteUseCase deleteNoteUseCase = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String noteId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.androidatlas.notes.feature.noteeditor.NoteEditorUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.androidatlas.notes.feature.noteeditor.NoteEditorUiState> uiState = null;
    
    @javax.inject.Inject()
    public NoteEditorViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.core.database.repository.NoteRepository noteRepository, @org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.feature.noteeditor.SaveNoteUseCase saveNoteUseCase, @org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.feature.noteeditor.DeleteNoteUseCase deleteNoteUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNoteId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.androidatlas.notes.feature.noteeditor.NoteEditorUiState> getUiState() {
        return null;
    }
    
    private final void loadNote(java.lang.String id) {
    }
    
    public final void updateTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String title) {
    }
    
    public final void updateContent(@org.jetbrains.annotations.NotNull()
    java.lang.String content) {
    }
    
    public final void updateLabel(@org.jetbrains.annotations.Nullable()
    java.lang.String label) {
    }
    
    public final void saveNote() {
    }
    
    public final void deleteNote() {
    }
    
    public final void clearError() {
    }
}