package com.androidatlas.notes.feature.noteslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidatlas.notes.core.common.model.Note
import com.androidatlas.notes.core.common.model.SyncQueueStatus
import com.androidatlas.notes.core.database.repository.NoteRepository
import com.androidatlas.notes.core.sync.processor.SyncQueueProcessor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotesListUiState(
    val notes: List<Note> = emptyList(),
    val searchQuery: String = "",
    val syncStatus: SyncQueueStatus = SyncQueueStatus(0, false, null),
    val isLoading: Boolean = false
)

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val syncQueueProcessor: SyncQueueProcessor
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val uiState: Flow<NotesListUiState> = combine(
        _searchQuery,
        _searchQuery.flatMapLatest { query ->
            if (query.isBlank()) {
                noteRepository.getAllNotes()
            } else {
                noteRepository.searchNotes(query)
            }
        },
        syncQueueProcessor.observeQueueStatus()
    ) { query, allNotes, syncStatus ->
        NotesListUiState(
            notes = allNotes,
            searchQuery = query,
            syncStatus = syncStatus,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Lazily,
        initialValue = NotesListUiState()
    )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearSearchQuery() {
        _searchQuery.value = ""
    }
}
