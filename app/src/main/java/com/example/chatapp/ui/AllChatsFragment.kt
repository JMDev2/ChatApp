package com.example.chatapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.databinding.FragmentAllChatsBinding
import com.example.chatapp.databinding.FragmentChatsBinding
import com.example.chatapp.models.AuthResponse
import com.example.chatapp.utils.SharedPreference.getToken
import com.example.personalexpenditure.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllChatsFragment : Fragment() {
    private lateinit var binding: FragmentAllChatsBinding
    private lateinit var chatAdapter: ChatAdapter
   // private val args: AllChatsFragmentArgs by navArgs()

    private val viewModel: ChatViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //retrieving the auth token from shared pref
        val token = getToken(requireContext())
        Log.d("TokenBeforeRequest", token.toString())
        viewModel.getAllMessages(token.toString())

        obcerveChats()

    }

    private fun onChatClick(){
        chatAdapter.onItemClick = { chat ->
            val bundle = Bundle()
            bundle.putInt("chat", chat.thread_id)
            Log.d("chat","uuuu: ${chat.thread_id}")

            requireView().findNavController().navigate(R.id.chatDetailsFragment, bundle)
        }
    }

    private fun setRecyclerView() {
        binding.chatRecyclerview.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = chatAdapter
        }

    }



    private fun obcerveChats(){
        viewModel.observeChatsLiveData().observe(viewLifecycleOwner) { chatsResponse ->
            when(chatsResponse.status){
                Status.SUCCESS ->{
                    hideProgressBar()
                    val chats = chatsResponse.data

                    chats?.let {
                        chatAdapter = ChatAdapter(it)
                        setRecyclerView()
                        onChatClick()
                    }

                }
                Status.LOADING -> {
                showProgressBar()

            }
                Status.ERROR ->{
                hideProgressBar()

            }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }






}