package com.androidatlas.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.androidatlas.notes.core.designsystem.theme.AtlasTheme
import com.androidatlas.notes.core.navigation.NavigationRoute
import com.androidatlas.notes.feature.noteeditor.NoteEditorScreen
import com.androidatlas.notes.feature.noteeditor.NoteEditorViewModel
import com.androidatlas.notes.feature.noteslist.NotesListScreen
import com.androidatlas.notes.feature.noteslist.NotesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AtlasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = NavigationRoute.NotesList.route,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Notes List screen
                        composable(NavigationRoute.NotesList.route) {
                            val viewModel: NotesListViewModel = hiltViewModel()
                            NotesListScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        // Note Editor screen
                        composable(
                            route = "note_editor?noteId={noteId}",
                            arguments = listOf(
                                navArgument("noteId") {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                }
                            )
                        ) { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getString("noteId")
                            val viewModel: NoteEditorViewModel = hiltViewModel()
                            NoteEditorScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
