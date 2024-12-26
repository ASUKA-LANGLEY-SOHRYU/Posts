package com.example.posts.domain.usecase

import com.example.posts.domain.model.auth.AuthResponse
import com.example.posts.domain.model.auth.RegistrationRequest
import com.example.posts.domain.repository.AuthRepository
import io.reactivex.Single

class RegistrationUseCase(
    private val authRepository: AuthRepository
) {
    fun execute(registrationRequest: RegistrationRequest): Single<AuthResponse> {
        return authRepository.register(registrationRequest)
    }
}