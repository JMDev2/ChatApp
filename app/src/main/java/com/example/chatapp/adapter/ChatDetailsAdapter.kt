package com.example.chatapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.AgentLayoutBinding
import com.example.chatapp.databinding.UserLayoutBinding
import com.example.chatapp.models.MessageResponseItem
import java.text.SimpleDateFormat
import java.util.*

class ChatDetailsAdapter(private val message: ArrayList<MessageResponseItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_RECEIVE = 1
    private val ITEM_SENT = 2

    inner class ReceiveViewHolder(val binding: AgentLayoutBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageResponseItem) {
            binding.receiveTxt.text = message.body
            val timestamp = message.timestamp // Replace with the actual timestamp from the chat message
            val formattedTime = timestamp?.let { formatTimestampToHHMM(it) }
            binding.receiveTxtTimeStamp.text = formattedTime
        }
    }

    inner class UserViewHolder(val binding: UserLayoutBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageResponseItem) {
            binding.userTxt.text = message.body
            val timestamp =
                message.timestamp // Replace with the actual timestamp from the chat message
            val formattedTime = timestamp?.let { formatTimestampToHHMM(it) }
            binding.userTimeStamp.text = formattedTime
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = message[position]

        if (holder is UserViewHolder) {
            holder.bind(currentMessage)
        } else if (holder is ReceiveViewHolder) {
            holder.bind(currentMessage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = message[position]
        return if (currentMessage.agent_id == null) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_RECEIVE) {
            ReceiveViewHolder(
                AgentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                parent.context
            )
        } else {
            UserViewHolder(
                UserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                parent.context
            )
        }
    }

    override fun getItemCount(): Int = message.size

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
