package com.example.posts.domain.repository

import com.example.posts.domain.model.auth.AuthRequest
import com.example.posts.domain.model.auth.AuthResponse
import com.example.posts.domain.model.auth.RegistrationRequest
import io.reactivex.Single

interface AuthRepository {
    fun register(registrationRequest: RegistrationRequest): Single<AuthResponse>
    fun login(authRequest: AuthRequest): Single<AuthResponse>
}