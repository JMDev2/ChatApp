package com.example.chatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.AgentLayoutBinding
import com.example.chatapp.databinding.UserLayoutBinding
import com.example.chatapp.models.MessageResponseItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatDetailsAdapter(private val messageList: ArrayList<MessageResponseItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val ITEM_RECEIVE = 1
    private val ITEM_SENT = 2


    inner class UserViewHolder(val binding: UserLayoutBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageResponseItem) {
            binding.userTxt.text = message.body
            val timestamp = message.timestamp
            val formattedTime = timestamp?.let { formatTimestampToHHMM(it) }
            binding.userTimeStamp.text = formattedTime
        }
    }
    inner class AgentViewHolder(val binding: AgentLayoutBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageResponseItem) {
            binding.receiveTxt.text = message.body
            val timestamp = message.timestamp
            val formattedTime = timestamp?.let { formatTimestampToHHMM(it) }
            binding.receiveTxtTimeStamp.text = formattedTime
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder is UserViewHolder) {
            holder.bind(currentMessage)
        } else if (holder is AgentViewHolder) {
            holder.bind(currentMessage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if (currentMessage.agent_id == null) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_RECEIVE) {
            AgentViewHolder(
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

    fun addMessage(message: MessageResponseItem) {
        messageList.add(message)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = messageList.size

    //format the time stamp
    fun formatTimestampToHHMM(timestamp: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("HH:mm")

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
