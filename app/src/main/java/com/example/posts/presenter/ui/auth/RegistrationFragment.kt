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
import com.example.posts.databinding.FragmentRegistrationBinding
import com.example.posts.presenter.ui.auth.vm.RegistrationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val vm by viewModel<RegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        initButtons()
        observeEditText()
    }

    private fun observeEditText(){
        binding.username.addTextChangedListener {
            vm.registrationRequest.value?.username = it.toString()
        }
        binding.password.addTextChangedListener {
            vm.registrationRequest.value?.password = it.toString()
        }
        binding.email.addTextChangedListener {
            vm.registrationRequest.value?.email = it.toString()
        }
        binding.confirmPassword.addTextChangedListener {
            vm.registrationRequest.value?.confirmPassword = it.toString()
        }
    }

    private fun observeLiveData(){
        vm.error.observe(viewLifecycleOwner) { error ->
            Log.i("Register Frag", error.toString())
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
                        .setPopUpTo(R.id.registrationFragment, true)
                        .build())
            }
        }
    }

    private fun initButtons(){
        binding.registerButton.setOnClickListener {
            vm.register()
        }
        binding.loginPrompt.setOnClickListener {
            findNavController().navigate(R.id.loginFragment, null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.registrationFragment, true)
                    .build())
        }
    }

}