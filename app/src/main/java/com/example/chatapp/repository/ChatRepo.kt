package com.example.chatapp.repository

import com.example.chatapp.api.ChatApiImpl
import com.example.chatapp.models.Login
import com.example.chatapp.models.SendMessageResponse
import com.example.personalexpenditure.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChatRepo @Inject constructor(private val api: ChatApiImpl) {
    suspend fun userLogin(login: Login) = flow{
        emit(Resource.loading(null))
        emit(api.postUserLogin(login)) //give th actual results
    }.flowOn(Dispatchers.IO)

    //get all messages
    suspend fun getMessages(authToken: String) = flow {
        emit(Resource.loading(null))
        emit(api.getAllMessages(authToken))
    }.flowOn(Dispatchers.IO)

    //send message
    suspend fun sendMessage(token: String, message: SendMessageResponse) = flow {
        emit(Resource.loading(null))
        emit(api.sendMessage(token,message))
    }.flowOn(Dispatchers.IO)

}