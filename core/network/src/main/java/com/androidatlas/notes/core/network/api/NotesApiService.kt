package com.androidatlas.notes.core.network.api

import com.androidatlas.notes.core.network.dto.NoteDto
import com.androidatlas.notes.core.network.dto.SyncRequestDto
import com.androidatlas.notes.core.network.dto.SyncResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Query

interface NotesApiService {
    @GET("notes")
    suspend fun listNotes(
        @Query("search") search: String = "",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 50
    ): NotesListResponseDto

    @GET("notes/{id}")
    suspend fun getNote(@Path("id") id: String): NoteDto

    @POST("notes/sync")
    suspend fun sync(@Body request: SyncRequestDto): SyncResponseDto
}

data class NotesListResponseDto(
    val notes: List<NoteDto>
)
