package com.example.posts.domain.repository

import com.example.posts.data.model.Post
import io.reactivex.Single

interface PostsRepository {
    fun createPost(imageId: Long, description: String): Single<Post>
    fun topPosts(page: Int, size: Int): Single<List<Post>>
    fun myPosts(page: Int, size: Int): Single<List<Post>>
}