package com.example.chatapp.api

import com.example.chatapp.models.AuthResponse
import com.example.chatapp.models.Login
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatService {
    @POST("api/login")
    suspend fun login(
        @Body login: Login
    ): Response<AuthResponse>
}