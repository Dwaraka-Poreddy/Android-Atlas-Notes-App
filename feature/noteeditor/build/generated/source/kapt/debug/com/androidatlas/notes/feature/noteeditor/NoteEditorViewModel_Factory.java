package com.androidatlas.notes.feature.noteeditor;

import androidx.lifecycle.SavedStateHandle;
import com.androidatlas.notes.core.database.repository.NoteRepository;
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
public final class NoteEditorViewModel_Factory implements Factory<NoteEditorViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<NoteRepository> noteRepositoryProvider;

  private final Provider<SaveNoteUseCase> saveNoteUseCaseProvider;

  private final Provider<DeleteNoteUseCase> deleteNoteUseCaseProvider;

  public NoteEditorViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<NoteRepository> noteRepositoryProvider,
      Provider<SaveNoteUseCase> saveNoteUseCaseProvider,
      Provider<DeleteNoteUseCase> deleteNoteUseCaseProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.noteRepositoryProvider = noteRepositoryProvider;
    this.saveNoteUseCaseProvider = saveNoteUseCaseProvider;
    this.deleteNoteUseCaseProvider = deleteNoteUseCaseProvider;
  }

  @Override
  public NoteEditorViewModel get() {
    return newInstance(savedStateHandleProvider.get(), noteRepositoryProvider.get(), saveNoteUseCaseProvider.get(), deleteNoteUseCaseProvider.get());
  }

  public static NoteEditorViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<NoteRepository> noteRepositoryProvider,
      Provider<SaveNoteUseCase> saveNoteUseCaseProvider,
      Provider<DeleteNoteUseCase> deleteNoteUseCaseProvider) {
    return new NoteEditorViewModel_Factory(savedStateHandleProvider, noteRepositoryProvider, saveNoteUseCaseProvider, deleteNoteUseCaseProvider);
  }

  public static NoteEditorViewModel newInstance(SavedStateHandle savedStateHandle,
      NoteRepository noteRepository, SaveNoteUseCase saveNoteUseCase,
      DeleteNoteUseCase deleteNoteUseCase) {
    return new NoteEditorViewModel(savedStateHandle, noteRepository, saveNoteUseCase, deleteNoteUseCase);
  }
}
