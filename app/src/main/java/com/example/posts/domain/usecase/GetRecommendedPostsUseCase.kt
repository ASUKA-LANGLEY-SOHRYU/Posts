package com.example.posts.domain.usecase

import com.example.posts.domain.provider.ImageUrlProvider
import com.example.posts.presenter.model.post.Post
import com.example.posts.domain.repository.PostsRepository
import io.reactivex.Single

class GetRecommendedPostsUseCase(
    private val postsRepository: PostsRepository,
    private val imageUrlProvider: ImageUrlProvider
) {
    fun execute(page: Int, size: Int): Single<List<Post>> {
        return postsRepository.topPosts(page, size)
            .map { it.map { post -> Post(
                imageUrl = imageUrlProvider.provide(post.imageId),
                description = post.description,
                likes = post.likes.toInt()
            )
        } }
    }
}