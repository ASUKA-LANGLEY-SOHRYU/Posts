package com.example.posts.presenter.ui.generate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.posts.R
import com.example.posts.databinding.FragmentGenerateBinding
import com.example.posts.presenter.model.post.Post
import com.example.posts.presenter.ui.generate.vm.GenerateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenerateFragment : Fragment() {

    private lateinit var binding: FragmentGenerateBinding
    private val vm by viewModel<GenerateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenerateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        observeLiveData()
    }

    private fun observeLiveData() {
        vm.imageId.observe(viewLifecycleOwner){
            Log.i("MYTAG", it.toString())
            if (it < 0)
                return@observe
            Glide.with(requireContext())
                .load(vm.getGlideUrl())
                .signature(ObjectKey(vm.getGlideUrl().hashCode().toString()))
                //.transform(RoundedCorners(10))
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.progress_bar_default)
                .into(binding.image)
            onOk()
        }
        vm.isError.observe(viewLifecycleOwner, Observer(::onError))
    }

    private fun initButtons() {
        binding.generate.setOnClickListener {
            vm.generate(binding.prompt.text.toString())
        }
        binding.post.setOnClickListener {
            vm.post()
        }
    }

    private fun initReloadButton() {
        binding.reload.setOnClickListener {
            onOk()
        }
    }

    private fun onOk() {
        binding.errorMessage.visibility = View.GONE
    }

    private fun onError(value: Boolean) {
        if (!value)
            return
        binding.apply {
            errorMessage.visibility = View.VISIBLE
            reload.visibility = View.VISIBLE

            errorMessage.text = vm.error.value
        }
    }

}