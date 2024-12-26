package com.example.posts.data.provider

import com.example.posts.domain.provider.ImageUrlProvider

class ImageUrlProviderImpl(
    private val baseUrl: String
) : ImageUrlProvider {
    override fun provide(imageId: Long): String {
        return "${baseUrl}api/images/${imageId}"
    }
}