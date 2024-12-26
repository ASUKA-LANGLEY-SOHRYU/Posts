package com.example.posts.data.repository

import com.example.posts.data.datasource.api.retrofit.PostsApiService
import com.example.posts.data.model.Post
import com.example.posts.data.model.PostCreationRequest
import com.example.posts.domain.repository.PostsRepository
import io.reactivex.Single

class DefaultPostsRepository(
    private val apiService: PostsApiService
): PostsRepository {
    override fun createPost(imageId: Long, description: String): Single<Post> {
        return apiService.createPost(PostCreationRequest(imageId, description))
    }

    override fun topPosts(page: Int, size: Int): Single<List<Post>> {
        return apiService.topPosts(page, size)
    }

    override fun myPosts(page: Int, size: Int): Single<List<Post>> {
        return apiService.myPosts(page, size)
    }
}