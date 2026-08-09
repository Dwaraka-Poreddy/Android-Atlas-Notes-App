package com.androidatlas.notes.core.navigation

sealed class NavigationRoute(val route: String) {
    object NotesList : NavigationRoute("notes_list")
    data class NoteEditor(val noteId: String? = null) : NavigationRoute(
        if (noteId == null) "note_editor" else "note_editor/$noteId"
    )
}