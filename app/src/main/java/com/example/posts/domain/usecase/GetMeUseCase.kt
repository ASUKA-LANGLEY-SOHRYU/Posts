package com.example.posts.domain.usecase

import com.example.posts.domain.model.User
import com.example.posts.domain.repository.UserRepository
import io.reactivex.Single

class GetMeUseCase(
    private val userRepository: UserRepository
) {
    fun execute(): Single<User> {
        return userRepository.getMe()
            .map { User(
                username = it.login!!,
                imageUrl = "",//TODO
                email = it.email!!
            ) }
    }
}