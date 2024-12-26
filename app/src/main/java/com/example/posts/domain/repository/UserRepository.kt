package com.example.posts.domain.repository

import com.example.posts.data.model.User
import io.reactivex.Single

interface UserRepository {
    fun getMe(): Single<User>
}