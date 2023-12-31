package com.example.chatapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.models.AuthResponse
import com.example.chatapp.models.Login
import com.example.chatapp.models.MessageResponse
import com.example.chatapp.models.SendMessageResponse
import com.example.chatapp.repository.ChatRepo
import com.example.personalexpenditure.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: ChatRepo) : ViewModel() {

    val chatLiveData = MutableLiveData<Resource<AuthResponse?>>()
    val displayChatLiveData = MutableLiveData<Resource<MessageResponse?>>()
    val sendMessageLiveData = MutableLiveData<Resource<SendMessageResponse?>>()

    //login method
    fun userLogin(login: Login) = viewModelScope.launch {
        repository.userLogin(login).collect { response ->
            chatLiveData.value = response  // Update the chatLiveData with the response value
        }
    }


    //get the chats
    fun getAllMessages(authToken: String) = viewModelScope.launch {
        repository.getMessages(authToken).collect{
            displayChatLiveData.postValue(it)
        }
    }

    fun observeChatsLiveData(): LiveData<Resource<MessageResponse?>>{
        return displayChatLiveData
    }

    //send message

    fun sendMessage(token: String,message: SendMessageResponse) = viewModelScope.launch {
        repository.sendMessage(token, message).collect { response ->
            sendMessageLiveData.value = response
        }
    }


}