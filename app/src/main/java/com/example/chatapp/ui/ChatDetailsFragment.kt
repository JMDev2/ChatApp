package com.example.chatapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.adapter.ChatDetailsAdapter
import com.example.chatapp.databinding.FragmentChatDetailsBinding
import com.example.chatapp.databinding.FragmentChatsBinding
import com.example.chatapp.models.MessageResponse
import com.example.chatapp.models.MessageResponseItem
import com.example.chatapp.models.SendMessageResponse
import com.example.chatapp.utils.SharedPreference
import com.example.personalexpenditure.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatDetailsFragment : Fragment() {
    private lateinit var binding: FragmentChatDetailsBinding
    private lateinit var chatDetailsAdapter: ChatDetailsAdapter

    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = SharedPreference.getToken(requireContext())
        Log.d("TokenBeforeRequest", token.toString())
        viewModel.getAllMessages(token.toString())

        observeChats()


    }

    private fun receiveChats(messages: MessageResponse) {
        // Extract received values from arguments
        val receivedThread_id = requireArguments().getInt("chat")
        val receivedUser = requireArguments().getParcelable<MessageResponseItem>("user_id")

        // Set user_id to the TextView if available
        receivedUser?.user_id?.let {
            binding.userId.text = it
        }

        Log.d("chat", "received1: $receivedThread_id")

        receivedThread_id?.let {
            // Filter messages based on received thread_id
            val filteredMessages = messages.filter { message ->
                message.thread_id == receivedThread_id
            }

            // Create an adapter with the filtered messages
            chatDetailsAdapter = ChatDetailsAdapter(filteredMessages as ArrayList<MessageResponseItem>)

        }

        // Send button click listener
        binding.sendBtn.setOnClickListener {
            val textMessage = binding.messageEdt.text.toString()
            receivedThread_id?.let {
                // Send the message using ViewModel
                viewModel.sendMessage(it.toString(), textMessage)
                Toast.makeText(requireContext(), "Message sent", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setRecyclerView() {
        binding.chatDetailsRecyclerview.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = chatDetailsAdapter
        }
    }

    private fun sendMessage(thread_id: String, body: String){
        val textMessage = binding.messageEdt.text

        if (textMessage.isNotEmpty()){
            viewModel.sendMessage(thread_id, body)
        }
    }

    private fun observeChats(){
        viewModel.observeChatsLiveData().observe(viewLifecycleOwner) { chatsResponse ->
            when(chatsResponse.status){
                Status.SUCCESS ->{
                   // hideProgressBar()
                    binding.progressBar3.visibility = View.GONE
                    val chats = chatsResponse.data

                    chats?.let {
                        receiveChats(chats)
                        setRecyclerView()
                    }

                }
                Status.LOADING -> {
                   // showProgressBar()
                    binding.progressBar3.visibility = View.VISIBLE

                }
                Status.ERROR ->{
                   // hideProgressBar()

                }
            }
        }
    }


}