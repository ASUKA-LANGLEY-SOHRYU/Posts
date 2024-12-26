package com.example.posts.domain.usecase

import com.example.posts.domain.provider.ImageUrlProvider
import com.example.posts.domain.repository.PostsRepository
import com.example.posts.presenter.model.post.Post
import io.reactivex.Single

class GetMyPostsUseCase(
    private val postsRepository: PostsRepository,
    private val imageUrlProvider: ImageUrlProvider
) {
    fun execute(page: Int, size: Int): Single<List<Post>> {
        return postsRepository.myPosts(page, size)
            .map { it.map { post -> Post(
                imageUrl = imageUrlProvider.provide(post.imageId),
                description = post.description,
                likes = post.likes.toInt()
            ) } }
    }
}