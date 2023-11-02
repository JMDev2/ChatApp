package com.example.chatapp.api

import com.example.chatapp.models.AuthResponse
import com.example.chatapp.models.Login
import com.example.chatapp.models.MessageResponse
import com.example.personalexpenditure.utils.Resource
import javax.inject.Inject

class ChatApiImpl @Inject constructor(private val api: ChatService) : ChatApi {
    suspend fun postUserLogin(login: Login): Resource<AuthResponse?> {
        val response = api.login(login)
        return if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            Resource.error("No data", null)
        }
    }

    //get all message
        suspend fun getAllMessages(authToken: String): Resource<MessageResponse?> {
            val response = api.getMessages(authToken)
            return if (response.isSuccessful) {
                Resource.success(response.body())
            } else {
                Resource.error("No Chats found", null)
            }

    }
}
