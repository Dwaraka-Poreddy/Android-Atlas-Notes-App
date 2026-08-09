package com.androidatlas.notes.feature.noteeditor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.androidatlas.notes.core.designsystem.component.AtlasPrimaryButton
import com.androidatlas.notes.core.designsystem.component.AtlasSecondaryButton
import com.androidatlas.notes.core.designsystem.component.AtlasTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorScreen(
    viewModel: NoteEditorViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            navController.popBackStack()
        }
    }

    // Show error snackbar
    uiState.error?.let { error ->
        val snackbarMessage = error
        navController.context.let {
            // In real implementation, show snackbar using LaunchedEffect
            // For simplicity, we'll just display it in the UI
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Edit Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    if (viewModel.noteId != null) {
                        IconButton(onClick = { viewModel.deleteNote() }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete note",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title field
            AtlasTextField(
                value = uiState.title,
                onValueChange = { viewModel.updateTitle(it) },
                label = "Title",
                placeholder = "Enter note title...",
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Label/Folder field
            AtlasTextField(
                value = uiState.folderOrLabel ?: "",
                onValueChange = { viewModel.updateLabel(it.ifBlank { null }) },
                label = "Label / Folder (optional)",
                placeholder = "e.g., Work, Personal",
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Content field
            AtlasTextField(
                value = uiState.content,
                onValueChange = { viewModel.updateContent(it) },
                label = "Content",
                placeholder = "Enter note content...",
                singleLine = false,
                maxLines = 20,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            // Error message
            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

            // Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AtlasSecondaryButton(
                    text = "Cancel",
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f)
                )

                AtlasPrimaryButton(
                    text = "Save",
                    onClick = { viewModel.saveNote() },
                    enabled = !uiState.isLoading,
                    isLoading = uiState.isLoading,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
