package com.example.posts.data.repository

import com.example.posts.data.datasource.api.retrofit.PostsApiService
import com.example.posts.data.model.User
import com.example.posts.domain.repository.UserRepository
import io.reactivex.Single

class DefaultUserRepository(
    private val apiService: PostsApiService
):  UserRepository{
    override fun getMe(): Single<User> {
        return apiService.getMe()
    }
}