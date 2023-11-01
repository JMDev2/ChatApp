package com.example.chatapp.repository

import com.example.chatapp.api.ChatApiImpl
import com.example.chatapp.models.Login
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
}