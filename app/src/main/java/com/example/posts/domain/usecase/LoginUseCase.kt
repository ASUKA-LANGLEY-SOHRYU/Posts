package com.example.posts.domain.usecase

import com.example.posts.domain.model.auth.AuthRequest
import com.example.posts.domain.model.auth.AuthResponse
import com.example.posts.domain.repository.AuthRepository
import io.reactivex.Single

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    fun execute(authRequest: AuthRequest) : Single<AuthResponse>{
        return authRepository.login(authRequest)
    }
}