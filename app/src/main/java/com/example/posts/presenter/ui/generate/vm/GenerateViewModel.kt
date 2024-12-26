package com.example.posts.presenter.ui.generate.vm

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.model.GlideUrl
import com.example.posts.data.provider.GlideUrlProvider
import com.example.posts.domain.provider.ImageUrlProvider
import com.example.posts.domain.usecase.CheckGeneratedImageUseCase
import com.example.posts.domain.usecase.CreatePostUseCase
import com.example.posts.domain.usecase.GenerateImageUseCase
import com.example.posts.presenter.model.post.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Timer
import kotlin.concurrent.schedule

class GenerateViewModel(
    private val generateImageUseCase: GenerateImageUseCase,
    private val checkGeneratedImageUseCase: CheckGeneratedImageUseCase,
    private val createPostUseCase: CreatePostUseCase,
    private val glideUrlProvider: GlideUrlProvider
) : ViewModel() {

    private var timer: Timer? = null

    private val _imageId = MutableLiveData<Long>(-1)
    val imageId: LiveData<Long> = _imageId

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _error = MutableLiveData("")
    val error: LiveData<String> = _error

    private val _prompt = MutableLiveData<String>("")
    val prompt: LiveData<String> = _prompt

    fun generate(prompt: String?){
        if (prompt.isNullOrEmpty())
            return
        _prompt.value = "$prompt"
        generateImageUseCase.execute(prompt)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.i("GenerateViewModel", "Gen OK")
            }, {
                _isError.value = true
                _error.value = it.message
            })
        timer = Timer()
        var count = 0
        timer?.schedule(5000, 5000) {
            count += 1
            if (check() || count > 7)
                cancel()
        }
    }

    private fun check(): Boolean {
        var result = false
        checkGeneratedImageUseCase.execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.i("AAAA", it.toString())
                if (it.status == "DONE") {
                    _imageId.value = it.image?.id?.toLong()
                    Log.i("AAAAAAA", _imageId.value.toString())
                    result = true
                }
            }, {
                _isError.value = true
                _error.value = it.message
                result = true
            })
        return result
    }

    fun post(){
        createPostUseCase.execute(_imageId.value!!, _prompt.value!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.i("GenerateViewModel", "Post OK")
            }, {
                _isError.value = true
                _error.value = it.message
            })
    }

    fun getGlideUrl(): GlideUrl {
        return glideUrlProvider.provide(_imageId.value!!)
    }
}