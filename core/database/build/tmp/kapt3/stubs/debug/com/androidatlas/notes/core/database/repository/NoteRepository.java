package com.androidatlas.notes.core.database.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\rJ\u0016\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\r2\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0014J\u001a\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r2\u0006\u0010\u0016\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/androidatlas/notes/core/database/repository/NoteRepository;", "", "noteDao", "Lcom/androidatlas/notes/core/database/dao/NoteDao;", "noteEntityMapper", "Lcom/androidatlas/notes/core/database/mapper/NoteEntityMapper;", "(Lcom/androidatlas/notes/core/database/dao/NoteDao;Lcom/androidatlas/notes/core/database/mapper/NoteEntityMapper;)V", "deleteNote", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllNotes", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/androidatlas/notes/core/common/model/Note;", "getNote", "saveNote", "note", "Lcom/androidatlas/notes/core/database/entity/NoteEntity;", "(Lcom/androidatlas/notes/core/database/entity/NoteEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchNotes", "query", "database_debug"})
public final class NoteRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.androidatlas.notes.core.database.dao.NoteDao noteDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.androidatlas.notes.core.database.mapper.NoteEntityMapper noteEntityMapper = null;
    
    public NoteRepository(@org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.core.database.dao.NoteDao noteDao, @org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.core.database.mapper.NoteEntityMapper noteEntityMapper) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.androidatlas.notes.core.common.model.Note>> getAllNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.androidatlas.notes.core.common.model.Note> getNote(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.androidatlas.notes.core.common.model.Note>> searchNotes(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveNote(@org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.core.database.entity.NoteEntity note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteNote(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}