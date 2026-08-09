package com.androidatlas.notes.core.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.androidatlas.notes.core.database.entity.SyncOperationEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SyncOperationDao_Impl implements SyncOperationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SyncOperationEntity> __insertionAdapterOfSyncOperationEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOperation;

  private final SharedSQLiteStatement __preparedStmtOfUpdateOperationStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeletePendingOperationsForNote;

  public SyncOperationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSyncOperationEntity = new EntityInsertionAdapter<SyncOperationEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `sync_operations` (`id`,`localId`,`operationType`,`noteId`,`title`,`content`,`folderOrLabel`,`clientUpdatedAt`,`status`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SyncOperationEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getLocalId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getLocalId());
        }
        if (entity.getOperationType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getOperationType());
        }
        if (entity.getNoteId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNoteId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTitle());
        }
        if (entity.getContent() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getContent());
        }
        if (entity.getFolderOrLabel() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFolderOrLabel());
        }
        if (entity.getClientUpdatedAt() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getClientUpdatedAt());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getStatus());
        }
      }
    };
    this.__preparedStmtOfDeleteOperation = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM sync_operations WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateOperationStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE sync_operations SET status = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePendingOperationsForNote = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM sync_operations WHERE noteId = ? AND status = 'PENDING'";
        return _query;
      }
    };
  }

  @Override
  public Object enqueueOperation(final SyncOperationEntity operation,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSyncOperationEntity.insert(operation);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteOperation(final long id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOperation.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOperation.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object updateOperationStatus(final long id, final String status,
      final Continuation<? super Unit> arg2) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateOperationStatus.acquire();
        int _argIndex = 1;
        if (status == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, status);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateOperationStatus.release(_stmt);
        }
      }
    }, arg2);
  }

  @Override
  public Object deletePendingOperationsForNote(final String noteId,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePendingOperationsForNote.acquire();
        int _argIndex = 1;
        if (noteId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, noteId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePendingOperationsForNote.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object getPendingOperations(final Continuation<? super List<SyncOperationEntity>> arg0) {
    final String _sql = "SELECT * FROM sync_operations WHERE status = 'PENDING' ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SyncOperationEntity>>() {
      @Override
      @NonNull
      public List<SyncOperationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLocalId = CursorUtil.getColumnIndexOrThrow(_cursor, "localId");
          final int _cursorIndexOfOperationType = CursorUtil.getColumnIndexOrThrow(_cursor, "operationType");
          final int _cursorIndexOfNoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "noteId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfFolderOrLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "folderOrLabel");
          final int _cursorIndexOfClientUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "clientUpdatedAt");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<SyncOperationEntity> _result = new ArrayList<SyncOperationEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SyncOperationEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpLocalId;
            if (_cursor.isNull(_cursorIndexOfLocalId)) {
              _tmpLocalId = null;
            } else {
              _tmpLocalId = _cursor.getString(_cursorIndexOfLocalId);
            }
            final String _tmpOperationType;
            if (_cursor.isNull(_cursorIndexOfOperationType)) {
              _tmpOperationType = null;
            } else {
              _tmpOperationType = _cursor.getString(_cursorIndexOfOperationType);
            }
            final String _tmpNoteId;
            if (_cursor.isNull(_cursorIndexOfNoteId)) {
              _tmpNoteId = null;
            } else {
              _tmpNoteId = _cursor.getString(_cursorIndexOfNoteId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final String _tmpFolderOrLabel;
            if (_cursor.isNull(_cursorIndexOfFolderOrLabel)) {
              _tmpFolderOrLabel = null;
            } else {
              _tmpFolderOrLabel = _cursor.getString(_cursorIndexOfFolderOrLabel);
            }
            final String _tmpClientUpdatedAt;
            if (_cursor.isNull(_cursorIndexOfClientUpdatedAt)) {
              _tmpClientUpdatedAt = null;
            } else {
              _tmpClientUpdatedAt = _cursor.getString(_cursorIndexOfClientUpdatedAt);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item = new SyncOperationEntity(_tmpId,_tmpLocalId,_tmpOperationType,_tmpNoteId,_tmpTitle,_tmpContent,_tmpFolderOrLabel,_tmpClientUpdatedAt,_tmpStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Flow<Integer> observePendingCount() {
    final String _sql = "SELECT COUNT(*) FROM sync_operations WHERE status IN ('PENDING', 'SYNCING')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sync_operations"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
