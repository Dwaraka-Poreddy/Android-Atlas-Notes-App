package com.androidatlas.notes.core.network.api

import com.androidatlas.notes.core.network.dto.AuthTokensDto
import com.androidatlas.notes.core.network.dto.LoginRequestDto
import com.androidatlas.notes.core.network.dto.LogoutRequestDto
import com.androidatlas.notes.core.network.dto.RefreshTokenRequestDto
import com.androidatlas.notes.core.network.dto.RegisterRequestDto
import com.androidatlas.notes.core.network.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequestDto): AuthTokensDto

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): AuthTokensDto

    @POST("auth/refresh")
    suspend fun refresh(@Body request: RefreshTokenRequestDto): AuthTokensDto

    @POST("auth/logout")
    suspend fun logout(@Body request: LogoutRequestDto)

    @GET("auth/me")
    suspend fun getMe(@Header("Authorization") authHeader: String): UserDto
}
