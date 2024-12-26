package com.example.posts.domain.provider

import com.example.posts.domain.model.posts.PostsRequest

class PostsRequestProvider {
    private var postsRequest = PostsRequest(page = 0, size = 10)

    fun providePostsRequest(): PostsRequest {
        return postsRequest
    }

    fun changeRequest(postsRequest: PostsRequest) {
        this.postsRequest = postsRequest
    }

    fun getPage() : Int {
        return postsRequest.page
    }
}