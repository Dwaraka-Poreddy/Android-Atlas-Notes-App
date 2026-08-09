package com.androidatlas.notes.core.navigation

import androidx.navigation.NavController

fun NavController.navigateToNotesList() {
    navigate(NavigationRoute.NotesList.route) {
        popUpTo(NavigationRoute.NotesList.route) { inclusive = true }
    }
}

fun NavController.navigateToNoteEditor(noteId: String? = null) {
    val route = if (noteId != null) {
        "note_editor?noteId=$noteId"
    } else {
        "note_editor"
    }
    navigate(route)
}

fun NavController.navigateToNoteEditorWithResult(noteId: String? = null) {
    val route = if (noteId != null) {
        "note_editor?noteId=$noteId"
    } else {
        "note_editor"
    }
    navigate(route) {
        launchSingleTop = true
    }
}

fun String.extractNoteId(): String? {
    return if (contains("/")) {
        substringAfterLast("/")
    } else {
        null
    }
}
