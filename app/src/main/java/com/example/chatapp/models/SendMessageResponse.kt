package com.example.chatapp.models

data class SendMessageResponse(
    val agent_id: String,
    val body: String,
    val id: Int,
    val thread_id: Int,
    val timestamp: String,
    val user_id: String
)