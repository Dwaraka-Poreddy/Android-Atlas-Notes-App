package com.androidatlas.notes.core.network.interceptor

class TokenManager {
    var accessToken: String? = null
    var refreshToken: String? = null

    fun setTokens(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

    fun clearTokens() {
        this.accessToken = null
        this.refreshToken = null
    }
}
