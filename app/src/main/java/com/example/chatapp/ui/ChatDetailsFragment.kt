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
       // setRecyclerView()
        observeChats()
      //  sendMessage(receivedThread_id.toString(), textMessage.toString())

    }

    private fun receiveChats(messages: MessageResponse){

        val receivedThread_id = requireArguments().getInt("chat")
        val receivedUser_id = requireArguments().getParcelable<MessageResponseItem>("user_id")
        val textMessage = binding.messageEdt.text

        receivedUser_id?.let {
            binding.userId.text = receivedUser_id.user_id
        }


        Log.d("chat","received1:$receivedThread_id")
        receivedThread_id?.let {
            val filteredMessages = messages.filter { message ->
                message.thread_id == receivedThread_id
            }


            chatDetailsAdapter = ChatDetailsAdapter(filteredMessages as ArrayList<MessageResponseItem>)
            Log.e("chatii","received2:${filteredMessages}")
        }
        binding.sendBtn.setOnClickListener {
            viewModel.sendMessage(receivedThread_id.toString(), textMessage.toString())
            Toast.makeText(requireContext(), "sent", Toast.LENGTH_SHORT).show()
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
                    val chats = chatsResponse.data

                    chats?.let {
                       // chatDetailsAdapter = ChatDetailsAdapter(it)
                        receiveChats(chats)
                        setRecyclerView()
                    }

                }
                Status.LOADING -> {
                   // showProgressBar()

                }
                Status.ERROR ->{
                   // hideProgressBar()

                }
            }
        }
    }


}