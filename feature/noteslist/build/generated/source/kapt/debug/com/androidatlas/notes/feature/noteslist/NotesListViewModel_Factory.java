package com.androidatlas.notes.feature.noteslist;

import com.androidatlas.notes.core.database.repository.NoteRepository;
import com.androidatlas.notes.core.sync.processor.SyncQueueProcessor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class NotesListViewModel_Factory implements Factory<NotesListViewModel> {
  private final Provider<NoteRepository> noteRepositoryProvider;

  private final Provider<SyncQueueProcessor> syncQueueProcessorProvider;

  public NotesListViewModel_Factory(Provider<NoteRepository> noteRepositoryProvider,
      Provider<SyncQueueProcessor> syncQueueProcessorProvider) {
    this.noteRepositoryProvider = noteRepositoryProvider;
    this.syncQueueProcessorProvider = syncQueueProcessorProvider;
  }

  @Override
  public NotesListViewModel get() {
    return newInstance(noteRepositoryProvider.get(), syncQueueProcessorProvider.get());
  }

  public static NotesListViewModel_Factory create(Provider<NoteRepository> noteRepositoryProvider,
      Provider<SyncQueueProcessor> syncQueueProcessorProvider) {
    return new NotesListViewModel_Factory(noteRepositoryProvider, syncQueueProcessorProvider);
  }

  public static NotesListViewModel newInstance(NoteRepository noteRepository,
      SyncQueueProcessor syncQueueProcessor) {
    return new NotesListViewModel(noteRepository, syncQueueProcessor);
  }
}
