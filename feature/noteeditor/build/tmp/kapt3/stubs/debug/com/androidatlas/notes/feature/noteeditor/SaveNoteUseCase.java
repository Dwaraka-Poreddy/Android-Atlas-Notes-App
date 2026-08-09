package com.androidatlas.notes.feature.noteeditor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J6\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\nH\u0086@\u00a2\u0006\u0002\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/androidatlas/notes/feature/noteeditor/SaveNoteUseCase;", "", "noteRepository", "Lcom/androidatlas/notes/core/database/repository/NoteRepository;", "syncQueueProcessor", "Lcom/androidatlas/notes/core/sync/processor/SyncQueueProcessor;", "(Lcom/androidatlas/notes/core/database/repository/NoteRepository;Lcom/androidatlas/notes/core/sync/processor/SyncQueueProcessor;)V", "execute", "", "id", "", "title", "content", "folderOrLabel", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "noteeditor_debug"})
public final class SaveNoteUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.androidatlas.notes.core.database.repository.NoteRepository noteRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.androidatlas.notes.core.sync.processor.SyncQueueProcessor syncQueueProcessor = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "SaveNoteUseCase";
    @org.jetbrains.annotations.NotNull()
    public static final com.androidatlas.notes.feature.noteeditor.SaveNoteUseCase.Companion Companion = null;
    
    public SaveNoteUseCase(@org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.core.database.repository.NoteRepository noteRepository, @org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.core.sync.processor.SyncQueueProcessor syncQueueProcessor) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object execute(@org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String content, @org.jetbrains.annotations.Nullable()
    java.lang.String folderOrLabel, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/androidatlas/notes/feature/noteeditor/SaveNoteUseCase$Companion;", "", "()V", "TAG", "", "noteeditor_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}