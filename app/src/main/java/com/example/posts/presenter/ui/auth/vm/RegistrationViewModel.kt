package com.example.posts.presenter.ui.auth.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.posts.domain.repository.TokenRepository
import com.example.posts.domain.usecase.RegistrationUseCase
import com.example.posts.presenter.model.auth.LoginRequestFields
import com.example.posts.presenter.model.auth.RegistrationRequestFields
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class RegistrationViewModel(
    private val registrationUseCase: RegistrationUseCase,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val _registrationRequest = MutableLiveData<RegistrationRequestFields>(RegistrationRequestFields())
    val registrationRequest: LiveData<RegistrationRequestFields> = _registrationRequest

    private val _error = MutableLiveData<MutableList<String>>(mutableListOf())
    val error: LiveData<MutableList<String>> = _error

    private val _loginSuccess = MutableLiveData<Boolean>(false)
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    init {
        checkIfTokenPresents()
    }

    private fun checkIfTokenPresents() {
        val token = tokenRepository.load()
        if (!token.isNullOrEmpty())
            _loginSuccess.value = true
    }

    fun register(){
        val errors = mutableListOf<String>()
        if (_registrationRequest.value?.username.isNullOrBlank())
            errors.add("Username is required")
        if (_registrationRequest.value?.password.isNullOrBlank())
            errors.add("Password is required")
        if (_registrationRequest.value?.confirmPassword.isNullOrBlank())
            errors.add("Confirm password is required")
        if (_registrationRequest.value?.email.isNullOrBlank())
            errors.add("Email is required")

        if (_registrationRequest.value?.password != _registrationRequest.value?.confirmPassword)
            errors.add("Passwords are not the same!")
        _error.value = errors
        Log.i("Register", error.value.toString())
        if (errors.size > 0) {
            return
        }

        if (_error.value?.size == 0 && _registrationRequest.value != null)
            registrationUseCase.execute(_registrationRequest.value!!.toRegistrationRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        tokenRepository.save(it.token)
                        _loginSuccess.value = true
                        _error.value = mutableListOf()
                    },
                    { throwable ->
                        _loginSuccess.value = false
                        handleError(throwable)
                    }
                ).let { disposable ->
                    disposables.add(disposable)
                }
    }

    private fun handleError(throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                val errorBody = throwable.response()?.errorBody()?.string()
                _error.value?.add("Error: ${throwable.code()}: $errorBody")
            }
            is IOException -> {
                _error.value?.add("Network error. Please try again.")
            }
            else -> {
                _error.value?.add("An unknown error occurred.")
            }
        }
    }

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}