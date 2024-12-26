package com.example.posts.data.repository

import com.example.posts.data.datasource.api.retrofit.PostsApiService
import com.example.posts.domain.model.auth.AuthRequest
import com.example.posts.domain.model.auth.AuthResponse
import com.example.posts.domain.model.auth.RegistrationRequest
import com.example.posts.domain.repository.AuthRepository
import io.reactivex.Single

class DefaultAuthRepository(
    private val postsApiService: PostsApiService
) : AuthRepository {
    override fun register(registrationRequest: RegistrationRequest): Single<AuthResponse> {
        return postsApiService.register(registrationRequest)
    }

    override fun login(authRequest: AuthRequest): Single<AuthResponse> {
        return postsApiService.auth(authRequest)
    }
}