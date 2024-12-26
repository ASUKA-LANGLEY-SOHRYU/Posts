package com.example.posts.presenter.ui.profile.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.posts.data.provider.GlideUrlProvider
import com.example.posts.domain.model.User
import com.example.posts.domain.model.posts.PostsRequest
import com.example.posts.domain.provider.PostsRequestProvider
import com.example.posts.domain.usecase.GetMeUseCase
import com.example.posts.domain.usecase.GetMyPostsUseCase
import com.example.posts.presenter.adapter.PostsAdapter
import com.example.posts.presenter.model.post.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(
    private val postsRequestProvider: PostsRequestProvider,
    private val getMeUseCase: GetMeUseCase,
    private val getMyPostsUseCase: GetMyPostsUseCase,
    private val glideUrlProvider: GlideUrlProvider
) : ViewModel() {
    private val _posts = MutableLiveData<MutableList<Post>>(mutableListOf())
    val posts: LiveData<MutableList<Post>> = _posts

    private val _user = MutableLiveData<User>(User("","",""))
    val user: LiveData<User> = _user

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _error = MutableLiveData("")
    val error: LiveData<String> = _error

    private val _postsRequest = MutableLiveData(postsRequestProvider.providePostsRequest())
    val postsRequest: LiveData<PostsRequest> = _postsRequest

    init {
        loadMe()
    }

    fun getUrlProvider(): GlideUrlProvider{
        return glideUrlProvider
    }

    fun loadMe(){
        getMeUseCase.execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _user.value = it
                _user.notifyObserver()
            }, {
                _isError.value = true
                _error.value = it.message
            })
    }

    fun loadPosts() {
        getMyPostsUseCase.execute(_postsRequest.value!!.page, _postsRequest.value!!.size)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _posts.value?.addAll(it)
                _posts.notifyObserver()
            }, {
                _isError.value = true
                _error.value = it.message
            })
    }


    fun loadMore() {
        val page = _postsRequest.value?.page!!
        postsRequestProvider.changeRequest(
            _postsRequest.value!!.withPage(page)
        )
        loadPosts()
    }

    fun loadAnime(request: PostsRequest) {
        postsRequestProvider.changeRequest(request)
        _posts.value = mutableListOf()
        loadPosts()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}