package com.example.posts.domain.usecase

import com.example.posts.data.model.Post
import com.example.posts.domain.repository.PostsRepository
import io.reactivex.Single

class CreatePostUseCase(
    private val postsRepository: PostsRepository
) {
    fun execute(imageId: Long, description: String): Single<Post>{
        return postsRepository.createPost(imageId, description)
    }
}