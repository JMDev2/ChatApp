package com.example.chatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ReceiveLayoutBinding
import com.example.chatapp.models.SendMessageResponse

class ChatDetailsAdapter(private val chats: ArrayList<SendMessageResponse>):
RecyclerView.Adapter<ChatDetailsAdapter.ChatDetailsViewHolder>(){

    inner class ChatDetailsViewHolder(val binding: ReceiveLayoutBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        fun bind(
        message: SendMessageResponse
        ){
            binding.receivedMessageTv.text = message.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatDetailsViewHolder {
        return ChatDetailsViewHolder(
            ReceiveLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context
        )
    }

    override fun onBindViewHolder(holder: ChatDetailsViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount(): Int = chats.size
}
