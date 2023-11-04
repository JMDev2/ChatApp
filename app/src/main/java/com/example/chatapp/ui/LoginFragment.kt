package com.example.chatapp.ui

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentLoginBinding
import com.example.chatapp.models.Login
import com.example.chatapp.utils.SharedPreference
import com.example.chatapp.utils.SharedPreference.saveToken
import com.example.personalexpenditure.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: ChatViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            userLogin()
        }

        observeChatLiveData()
    }

    private fun userLogin() {
        val email = "edwinkmuriithi@gmail.com"
        val reversedPassword = email.reversed()

        val username = binding.emailEdt.text.toString().trim()
        val password = binding.passwordEdt.text.toString().trim()

        if (username == email && password == reversedPassword) {
            val login = Login(username, password)
            viewModel.userLogin(login)
        } else {
            binding.errorTextView.visibility = View.VISIBLE
            binding.errorTextView.text = "Invalid Password"
        }
    }

    private fun observeChatLiveData() {
        viewModel.chatLiveData.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    //saving the auth token in shared pref
                    val authToken = response.data?.auth_token
                    Log.d("TokenBeforeSaving","Token1: $authToken")
                    if (authToken != null){
                        saveToken(requireContext(),authToken)
                        Log.d("TokenAfterSaving","Token2: $authToken")
                    }
                    // Handle a successful login response
                  //  binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.allChatsFragment)

                }
                Status.LOADING -> {
                 //   binding.progressBar.visibility = View.VISIBLE
                    // Handle loading state
                }
                Status.ERROR -> {
                    //val errorMessage = response.message
                 //   binding.errorTextView.visibility = View.VISIBLE
                 //   binding.progressBar.visibility = View.GONE
                    // Handle error state and display the error message
                }
            }
        }
    }
}
