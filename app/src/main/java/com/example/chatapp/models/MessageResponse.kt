package com.example.chatapp.models

import android.os.Parcel
import android.os.Parcelable

class MessageResponse : ArrayList<MessageResponseItem>()

data class MessageResponseItem(
    val agent_id: Any,
    val body: String?,
    val id: Int,
    val thread_id: Int,
    val timestamp: String?,
    val user_id: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("agent_id"),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(body)
        parcel.writeInt(id)
        parcel.writeInt(thread_id)
        parcel.writeString(timestamp)
        parcel.writeString(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageResponseItem> {
        override fun createFromParcel(parcel: Parcel): MessageResponseItem {
            return MessageResponseItem(parcel)
        }

        override fun newArray(size: Int): Array<MessageResponseItem?> {
            return arrayOfNulls(size)
        }
    }
}