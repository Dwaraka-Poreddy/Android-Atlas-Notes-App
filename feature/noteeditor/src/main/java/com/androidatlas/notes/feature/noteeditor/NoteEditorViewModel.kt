package com.androidatlas.notes.feature.noteeditor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidatlas.notes.core.common.model.Note
import com.androidatlas.notes.core.database.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NoteEditorUiState(
    val title: String = "",
    val content: String = "",
    val folderOrLabel: String? = null,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class NoteEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    val noteId: String? = savedStateHandle["noteId"]

    private val _uiState = MutableStateFlow(NoteEditorUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // Load note if editing existing note
        if (noteId != null) {
            loadNote(noteId)
        }
    }

    private fun loadNote(id: String) {
        viewModelScope.launch {
            try {
                noteRepository.getNote(id).collect { note ->
                    if (note != null) {
                        _uiState.value = _uiState.value.copy(
                            title = note.title,
                            content = note.content,
                            folderOrLabel = note.folderOrLabel
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load note: ${e.message}"
                )
            }
        }
    }

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title, isSaved = false)
    }

    fun updateContent(content: String) {
        _uiState.value = _uiState.value.copy(content = content, isSaved = false)
    }

    fun updateLabel(label: String?) {
        _uiState.value = _uiState.value.copy(folderOrLabel = label, isSaved = false)
    }

    fun saveNote() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                val currentState = _uiState.value
                if (currentState.title.isBlank() && currentState.content.isBlank()) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Note cannot be empty"
                    )
                    return@launch
                }

                saveNoteUseCase.execute(
                    id = noteId,
                    title = currentState.title.ifBlank { "Untitled" },
                    content = currentState.content,
                    folderOrLabel = currentState.folderOrLabel
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSaved = true,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to save note: ${e.message}"
                )
            }
        }
    }

    fun deleteNote() {
        if (noteId == null) return // Can't delete unsaved note

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                deleteNoteUseCase.execute(noteId)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSaved = true, // Mark as saved/deleted
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to delete note: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}