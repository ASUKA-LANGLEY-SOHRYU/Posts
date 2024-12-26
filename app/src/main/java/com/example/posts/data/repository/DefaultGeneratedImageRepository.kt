package com.example.posts.data.repository

import com.example.posts.data.datasource.api.retrofit.PostsApiService
import com.example.posts.data.model.Image
import com.example.posts.data.model.ImageResponse
import com.example.posts.domain.repository.GeneratedImageRepository
import io.reactivex.Single

class DefaultGeneratedImageRepository(
    private val apiService: PostsApiService
): GeneratedImageRepository {
    override fun generate(prompt: String): Single<String> {
        return apiService.generateImage(prompt)
    }

    override fun checkGeneration(): Single<ImageResponse> {
        return apiService.checkImage()
    }
}