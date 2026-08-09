package com.androidatlas.notes.core.network.dto

data class RegisterRequestDto(
    val email: String,
    val password: String
)

data class LoginRequestDto(
    val email: String,
    val password: String
)

data class RefreshTokenRequestDto(
    val refreshToken: String
)

data class LogoutRequestDto(
    val refreshToken: String
)

data class UserDto(
    val id: String,
    val email: String,
    val createdAt: String
)
