package com.example.chatapp.models

class MessageResponse : ArrayList<MessageResponseItem>()

data class MessageResponseItem(
    val agent_id: Any,
    val body: String,
    val id: Int,
    val thread_id: Int,
    val timestamp: String,
    val user_id: String
)