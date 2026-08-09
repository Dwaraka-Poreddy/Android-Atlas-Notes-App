package com.androidatlas.notes.core.network.retrofit

import com.androidatlas.notes.core.network.api.AuthApiService
import com.androidatlas.notes.core.network.api.NotesApiService
import com.androidatlas.notes.core.network.interceptor.AuthInterceptor
import com.androidatlas.notes.core.network.interceptor.TokenManager
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.4:8000"

    private val tokenManager = TokenManager()

    private val authRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    val authApiService: AuthApiService by lazy {
        authRetrofit.create(AuthApiService::class.java)
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(authApiService, tokenManager))
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    val notesApiService: NotesApiService by lazy {
        retrofit.create(NotesApiService::class.java)
    }

    fun setTokens(accessToken: String, refreshToken: String) {
        tokenManager.setTokens(accessToken, refreshToken)
    }

    fun clearTokens() {
        tokenManager.clearTokens()
    }
}
