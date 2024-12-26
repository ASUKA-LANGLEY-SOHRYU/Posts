package com.example.posts.data.provider

import com.bumptech.glide.load.model.GlideUrl
import com.example.posts.domain.provider.ImageUrlProvider
import com.example.posts.domain.repository.TokenRepository

class GlideUrlProvider(
    private val imageUrlProvider: ImageUrlProvider,
    private val tokenRepository: TokenRepository
) {
    fun provide(imageId: Long): GlideUrl{
        return GlideUrl(imageUrlProvider.provide(imageId)) { mapOf(Pair("Authorization", "Bearer ${tokenRepository.load()}")) }
    }
    fun provide(imageUrl: String): GlideUrl{
        return GlideUrl(imageUrl) { mapOf(Pair("Authorization", "Bearer ${tokenRepository.load()}")) }
    }
}