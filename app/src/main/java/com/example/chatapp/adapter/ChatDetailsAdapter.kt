package com.example.chatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ReceiveLayoutBinding
import com.example.chatapp.databinding.SentLayoutBinding
import com.example.chatapp.models.MessageResponseItem
import com.example.chatapp.models.SendMessageResponse

class ChatDetailsAdapter(private val message: ArrayList<MessageResponseItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_RECEIVE = 1
    private val ITEM_SENT = 2

    inner class ReceiveViewHolder(val binding: ReceiveLayoutBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageResponseItem) {
            binding.receiveTxt.text = message.body
        }
    }

    inner class SentViewHolder(val binding: SentLayoutBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageResponseItem) {
            binding.sendTxt.text = message.body
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = message[position]

        if (holder is SentViewHolder) {
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
                ReceiveLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                parent.context
            )
        } else {
            SentViewHolder(
                SentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                parent.context
            )
        }
    }

    override fun getItemCount(): Int = message.size
}
