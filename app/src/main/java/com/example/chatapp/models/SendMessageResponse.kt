package com.example.chatapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SendMessageResponse(
    val agent_id: String? = null,
    val body: String,
    val id: Int? = null,
    val thread_id: Int? = null,
    val timestamp: String? = null,
    val user_id: String? = null
) : Parcelable