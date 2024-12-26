package com.example.posts.presenter.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.posts.R
import com.example.posts.databinding.FragmentLoginBinding
import com.example.posts.presenter.ui.auth.vm.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val vm by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        observeEditText()
        initButtons()
    }

    private fun observeEditText() {
        binding.username.addTextChangedListener {
            vm.loginRequest.value?.username = it.toString()
        }

        binding.password.addTextChangedListener {
            vm.loginRequest.value?.password = it.toString()
        }
    }

    private fun observeLiveData(){
        vm.error.observe(viewLifecycleOwner) { error ->
            Log.i("LOGIN Frag", error.toString())
            if (error.isEmpty()) {
                binding.errorInfo.text = ""
                binding.errorInfo.visibility = View.INVISIBLE
            } else {
                binding.errorInfo.visibility = View.VISIBLE
                binding.errorInfo.text = error.joinToString("\n")
            }
        }
        vm.loginSuccess.observe(viewLifecycleOwner) { success ->
            if (success == true) {
                findNavController().navigate(R.id.recommendationsFragment, null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                        .build())
            }
        }
    }

    private fun initButtons(){
        binding.loginButton.setOnClickListener {
            vm.login()
        }
        binding.signupPrompt.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment, null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment, true)
                    .build())
        }
    }
}