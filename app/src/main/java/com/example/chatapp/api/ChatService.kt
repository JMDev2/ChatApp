package com.example.chatapp.api

import com.example.chatapp.models.AuthResponse
import com.example.chatapp.models.Login
import com.example.chatapp.models.MessageResponse
import com.example.chatapp.models.SendMessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatService {
    @POST("api/login")
    suspend fun login(
        @Body login: Login
    ): Response<AuthResponse>


    @GET("api/messages")
    suspend fun getMessages(
        @Header("X-Branch-Auth-Token") authToken: String
    ): Response<MessageResponse>

    //send message
    @POST("api/messages")
    suspend fun sendMessage(

    ):Response<SendMessageResponse>

}