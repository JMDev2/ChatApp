package com.example.chatapp.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
@Parcelize
class MessageResponse : ArrayList<MessageResponseItem>(), Parcelable

@Parcelize
data class MessageResponseItem(
    val agent_id: @RawValue Any? = null,
    val body: String?,
    val id: Int,
    val thread_id: Int,
    val timestamp: String?,
    val user_id: String?
) : Parcelable