package com.androidatlas.notes.core.network.interceptor

import android.util.Log
import com.androidatlas.notes.core.network.api.AuthApiService
import com.androidatlas.notes.core.network.dto.RefreshTokenRequestDto
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager
) : Interceptor {
    companion object {
        private const val TAG = "AuthInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        Log.d(TAG, "intercept() — ${originalRequest.method} ${originalRequest.url}")

        val hasToken = tokenManager.accessToken != null
        Log.d(TAG, "intercept() — hasAccessToken=$hasToken, hasRefreshToken=${tokenManager.refreshToken != null}")

        val requestWithToken = if (hasToken) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer ${tokenManager.accessToken}")
                .build()
        } else {
            originalRequest
        }

        val response = chain.proceed(requestWithToken)
        Log.d(TAG, "intercept() — response code=${response.code} for ${originalRequest.url}")

        if (response.code == 401) {
            Log.w(TAG, "intercept() — 401 received, attempting token refresh")
            response.close()
            val refreshToken = tokenManager.refreshToken
            if (refreshToken != null) {
                return try {
                    val newTokens = runBlocking {
                        authApiService.refresh(RefreshTokenRequestDto(refreshToken))
                    }
                    Log.d(TAG, "intercept() — token refresh succeeded")
                    tokenManager.setTokens(newTokens.accessToken, newTokens.refreshToken)

                    val retryRequest = originalRequest.newBuilder()
                        .addHeader("Authorization", "Bearer ${newTokens.accessToken}")
                        .build()
                    chain.proceed(retryRequest)
                } catch (e: Exception) {
                    Log.e(TAG, "intercept() — token refresh FAILED: ${e.message}", e)
                    response
                }
            } else {
                Log.w(TAG, "intercept() — no refresh token available, returning 401")
            }
        }

        return response
    }
}
