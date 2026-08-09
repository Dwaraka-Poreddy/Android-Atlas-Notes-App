package com.androidatlas.notes.core.database.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0018\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001c\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u000e\u001a\u00020\bH\'J\u001e\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u0011\u00a8\u0006\u0012"}, d2 = {"Lcom/androidatlas/notes/core/database/dao/NoteDao;", "", "getAllNotes", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/androidatlas/notes/core/database/entity/NoteEntity;", "getNoteById", "id", "", "insertOrUpdateNote", "", "note", "(Lcom/androidatlas/notes/core/database/entity/NoteEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchNotes", "query", "softDeleteNote", "syncStatus", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "database_debug"})
@androidx.room.Dao()
public abstract interface NoteDao {
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE deleted = 0 ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.androidatlas.notes.core.database.entity.NoteEntity>> getAllNotes();
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE id = :id AND deleted = 0")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.androidatlas.notes.core.database.entity.NoteEntity> getNoteById(@org.jetbrains.annotations.NotNull()
    java.lang.String id);
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE deleted = 0 AND (title LIKE \'%\' || :query || \'%\' OR content LIKE \'%\' || :query || \'%\') ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.androidatlas.notes.core.database.entity.NoteEntity>> searchNotes(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOrUpdateNote(@org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.core.database.entity.NoteEntity note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE notes SET deleted = 1, syncStatus = :syncStatus WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object softDeleteNote(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String syncStatus, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}