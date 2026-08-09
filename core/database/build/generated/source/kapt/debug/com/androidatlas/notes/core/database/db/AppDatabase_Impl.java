package com.androidatlas.notes.core.database.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.FtsTableInfo;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.androidatlas.notes.core.database.dao.NoteDao;
import com.androidatlas.notes.core.database.dao.NoteDao_Impl;
import com.androidatlas.notes.core.database.dao.SyncOperationDao;
import com.androidatlas.notes.core.database.dao.SyncOperationDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile NoteDao _noteDao;

  private volatile SyncOperationDao _syncOperationDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `content` TEXT NOT NULL, `folderOrLabel` TEXT, `updatedAt` TEXT NOT NULL, `deleted` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sync_operations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `localId` TEXT NOT NULL, `operationType` TEXT NOT NULL, `noteId` TEXT, `title` TEXT, `content` TEXT, `folderOrLabel` TEXT, `clientUpdatedAt` TEXT NOT NULL, `status` TEXT NOT NULL)");
        db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `notes_fts` USING FTS4(`title` TEXT NOT NULL, `content` TEXT NOT NULL, content=`notes`)");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_BEFORE_UPDATE BEFORE UPDATE ON `notes` BEGIN DELETE FROM `notes_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_BEFORE_DELETE BEFORE DELETE ON `notes` BEGIN DELETE FROM `notes_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_AFTER_UPDATE AFTER UPDATE ON `notes` BEGIN INSERT INTO `notes_fts`(`docid`, `title`, `content`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`content`); END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_AFTER_INSERT AFTER INSERT ON `notes` BEGIN INSERT INTO `notes_fts`(`docid`, `title`, `content`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`content`); END");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1bfd893fb36caeef21512ea2ab6a6eee')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `notes`");
        db.execSQL("DROP TABLE IF EXISTS `sync_operations`");
        db.execSQL("DROP TABLE IF EXISTS `notes_fts`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_BEFORE_UPDATE BEFORE UPDATE ON `notes` BEGIN DELETE FROM `notes_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_BEFORE_DELETE BEFORE DELETE ON `notes` BEGIN DELETE FROM `notes_fts` WHERE `docid`=OLD.`rowid`; END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_AFTER_UPDATE AFTER UPDATE ON `notes` BEGIN INSERT INTO `notes_fts`(`docid`, `title`, `content`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`content`); END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_notes_fts_AFTER_INSERT AFTER INSERT ON `notes` BEGIN INSERT INTO `notes_fts`(`docid`, `title`, `content`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`content`); END");
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(7);
        _columnsNotes.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("folderOrLabel", new TableInfo.Column("folderOrLabel", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("updatedAt", new TableInfo.Column("updatedAt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("deleted", new TableInfo.Column("deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNotes = new TableInfo("notes", _columnsNotes, _foreignKeysNotes, _indicesNotes);
        final TableInfo _existingNotes = TableInfo.read(db, "notes");
        if (!_infoNotes.equals(_existingNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "notes(com.androidatlas.notes.core.database.entity.NoteEntity).\n"
                  + " Expected:\n" + _infoNotes + "\n"
                  + " Found:\n" + _existingNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsSyncOperations = new HashMap<String, TableInfo.Column>(9);
        _columnsSyncOperations.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncOperations.put("localId", new TableInfo.Column("localId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncOperations.put("operationType", new TableInfo.Column("operationType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncOperations.put("noteId", new TableInfo.Column("noteId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncOperations.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncOperations.put("content", new TableInfo.Column("content", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncOperations.put("folderOrLabel", new TableInfo.Column("folderOrLabel", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncOperations.put("clientUpdatedAt", new TableInfo.Column("clientUpdatedAt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncOperations.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSyncOperations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSyncOperations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSyncOperations = new TableInfo("sync_operations", _columnsSyncOperations, _foreignKeysSyncOperations, _indicesSyncOperations);
        final TableInfo _existingSyncOperations = TableInfo.read(db, "sync_operations");
        if (!_infoSyncOperations.equals(_existingSyncOperations)) {
          return new RoomOpenHelper.ValidationResult(false, "sync_operations(com.androidatlas.notes.core.database.entity.SyncOperationEntity).\n"
                  + " Expected:\n" + _infoSyncOperations + "\n"
                  + " Found:\n" + _existingSyncOperations);
        }
        final HashSet<String> _columnsNotesFts = new HashSet<String>(3);
        _columnsNotesFts.add("title");
        _columnsNotesFts.add("content");
        final FtsTableInfo _infoNotesFts = new FtsTableInfo("notes_fts", _columnsNotesFts, "CREATE VIRTUAL TABLE IF NOT EXISTS `notes_fts` USING FTS4(`title` TEXT NOT NULL, `content` TEXT NOT NULL, content=`notes`)");
        final FtsTableInfo _existingNotesFts = FtsTableInfo.read(db, "notes_fts");
        if (!_infoNotesFts.equals(_existingNotesFts)) {
          return new RoomOpenHelper.ValidationResult(false, "notes_fts(com.androidatlas.notes.core.database.entity.NoteFts).\n"
                  + " Expected:\n" + _infoNotesFts + "\n"
                  + " Found:\n" + _existingNotesFts);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "1bfd893fb36caeef21512ea2ab6a6eee", "540b4328c44c77e91de55c11c0e3cd2a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(1);
    _shadowTablesMap.put("notes_fts", "notes");
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "notes","sync_operations","notes_fts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `notes`");
      _db.execSQL("DELETE FROM `sync_operations`");
      _db.execSQL("DELETE FROM `notes_fts`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(NoteDao.class, NoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SyncOperationDao.class, SyncOperationDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public NoteDao noteDao() {
    if (_noteDao != null) {
      return _noteDao;
    } else {
      synchronized(this) {
        if(_noteDao == null) {
          _noteDao = new NoteDao_Impl(this);
        }
        return _noteDao;
      }
    }
  }

  @Override
  public SyncOperationDao syncOperationDao() {
    if (_syncOperationDao != null) {
      return _syncOperationDao;
    } else {
      synchronized(this) {
        if(_syncOperationDao == null) {
          _syncOperationDao = new SyncOperationDao_Impl(this);
        }
        return _syncOperationDao;
      }
    }
  }
}
