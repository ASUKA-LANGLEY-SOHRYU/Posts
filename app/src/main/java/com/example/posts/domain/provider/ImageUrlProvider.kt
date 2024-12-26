package com.example.posts.domain.provider

interface ImageUrlProvider {
    fun provide(imageId: Long): String
}