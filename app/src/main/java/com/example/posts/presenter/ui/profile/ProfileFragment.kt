package com.example.posts.presenter.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.posts.databinding.FragmentProfileBinding
import com.example.posts.presenter.adapter.PostsAdapter
import com.example.posts.presenter.adapter.SpaceItemDecorator
import com.example.posts.presenter.model.post.Post
import com.example.posts.presenter.ui.profile.vm.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {

    private lateinit var postsAdapter: PostsAdapter
    private lateinit var binding: FragmentProfileBinding
    private val vm by viewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPosts()
        initReloadButton()
        observeLiveData()
    }

    private fun observeLiveData(){
        vm.posts.observe(viewLifecycleOwner, Observer(::onPostsDownloaded))
        vm.isError.observe(viewLifecycleOwner, Observer(::onError))
        vm.postsRequest.observe(viewLifecycleOwner) { vm.loadAnime(it) }
        vm.user.observe(viewLifecycleOwner){
            binding.username.text = it.username
            binding.email.text = it.email
        }
    }

    private fun initPosts() {
        Log.i("ABCABC", "kek")
        postsAdapter = PostsAdapter(vm.getUrlProvider())
        postsAdapter.setOnScrolledToTheEndListener(object :
            PostsAdapter.OnScrolledToTheEndListener {
            override fun onScrolledToTheEnd() {
                vm//
            }
        })
        binding.posts.adapter = postsAdapter
        binding.posts.addItemDecoration(SpaceItemDecorator(40))
    }

    private fun initReloadButton() {
        binding.reload.setOnClickListener {
            vm.loadPosts()
        }
    }

    private fun onPostsDownloaded(value: MutableList<Post>) {
        postsAdapter.setPosts(value)
        Log.i("ABCABC", value.toString())
        if (value.isNotEmpty()) {
            binding.progressBar.visibility = View.GONE
            binding.posts.visibility = View.VISIBLE
            binding.errorMessage.visibility = View.GONE
        }
    }

    private fun onError(value: Boolean) {
        Log.i("ABCABC", value.toString())
        if (!value)
            return
        binding.apply {
            errorMessage.visibility = View.VISIBLE
            reload.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
            binding.posts.visibility = View.INVISIBLE

            errorMessage.text = vm.error.value
        }
    }

}