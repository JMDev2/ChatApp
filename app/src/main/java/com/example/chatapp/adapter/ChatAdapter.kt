package com.example.chatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ChatLayoutBinding
import com.example.chatapp.models.MessageResponseItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatAdapter (private val chats: ArrayList<MessageResponseItem>):
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    lateinit var onItemClick: ((MessageResponseItem) -> Unit)

        inner class ChatViewHolder(val binding: ChatLayoutBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
            fun bind(message: MessageResponseItem){
                binding.message.text = message.body
                binding.userId.text = message.user_id.toString()


                val timestamp = message.timestamp // Replace with the actual timestamp from the chat message
                val formattedTime = timestamp?.let { formatTimestampToHHMM(it) }
                binding.timeStamp.text = formattedTime

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            ChatLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chats[position])

        holder.itemView.setOnClickListener {
            onItemClick.invoke(chats[position])
        }
    }

    override fun getItemCount(): Int = chats.size

//format the time stamp
    fun formatTimestampToHHMM(timestamp: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("HH:mm")

        // Set the input format's time zone to UTC to match the timestamp format
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        try {
            val date = inputFormat.parse(timestamp)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}