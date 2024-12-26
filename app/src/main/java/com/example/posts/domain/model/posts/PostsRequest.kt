package com.example.posts.domain.model.posts

data class PostsRequest(
    val page: Int,
    val size: Int
){
    fun withPage(page: Int): PostsRequest {
        return PostsRequest(page, size)
    }
}