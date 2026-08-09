package com.androidatlas.notes.core.database.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\'J\u001e\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0017\u00a8\u0006\u0018"}, d2 = {"Lcom/androidatlas/notes/core/database/dao/SyncOperationDao;", "", "deleteOperation", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deletePendingOperationsForNote", "noteId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "enqueueOperation", "operation", "Lcom/androidatlas/notes/core/database/entity/SyncOperationEntity;", "(Lcom/androidatlas/notes/core/database/entity/SyncOperationEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPendingOperations", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observePendingCount", "Lkotlinx/coroutines/flow/Flow;", "", "updateOperationStatus", "status", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "database_debug"})
@androidx.room.Dao()
public abstract interface SyncOperationDao {
    
    @androidx.room.Query(value = "SELECT * FROM sync_operations WHERE status = \'PENDING\' ORDER BY id ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPendingOperations(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.androidatlas.notes.core.database.entity.SyncOperationEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM sync_operations WHERE status IN (\'PENDING\', \'SYNCING\')")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> observePendingCount();
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object enqueueOperation(@org.jetbrains.annotations.NotNull()
    com.androidatlas.notes.core.database.entity.SyncOperationEntity operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM sync_operations WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteOperation(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE sync_operations SET status = :status WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateOperationStatus(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM sync_operations WHERE noteId = :noteId AND status = \'PENDING\'")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePendingOperationsForNote(@org.jetbrains.annotations.NotNull()
    java.lang.String noteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}