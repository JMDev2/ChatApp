package com.example.chatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.databinding.FragmentChatsBinding
import com.example.chatapp.ui.ChatViewModel
import com.example.personalexpenditure.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentChatsBinding
    private lateinit var chatAdapter: ChatAdapter

    private val viewModel: ChatViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatsBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        // Enable the back arrow in the toolbar
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Set the click listener for the back arrow
        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        obcerveChats()
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
                    }

                }Status.LOADING -> {
                    showProgressBar()

                }Status.ERROR ->{
                    hideProgressBar()

                }
            }
        }
    }

    private fun hideProgressBar() {
        binding?.progressBar?.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding?.progressBar?.visibility = View.VISIBLE
    }




}