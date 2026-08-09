package com.androidatlas.notes.feature.noteslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.androidatlas.notes.core.designsystem.component.AtlasNoteCard
import com.androidatlas.notes.core.designsystem.component.AtlasSyncIndicator
import com.androidatlas.notes.core.designsystem.component.AtlasTextField
import com.androidatlas.notes.core.designsystem.theme.AtlasTheme
import com.androidatlas.notes.core.navigation.navigateToNoteEditor

@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState(initial = NotesListUiState())
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigateToNoteEditor() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New note",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Search bar
            AtlasTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                label = "Search notes",
                placeholder = "Title or content...",
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Sync indicator
            AtlasSyncIndicator(
                pendingCount = uiState.syncStatus.pendingCount,
                isSyncing = uiState.syncStatus.isSyncing,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            // Notes list
            if (uiState.notes.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (searchQuery.isBlank()) "No notes yet" else "No results",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.notes) { note ->
                        AtlasNoteCard(
                            title = note.title.ifBlank { "Untitled" },
                            content = note.content,
                            label = note.folderOrLabel,
                            onClick = { navController.navigateToNoteEditor(note.id) }
                        )
                    }
                }
            }
        }
    }
}
