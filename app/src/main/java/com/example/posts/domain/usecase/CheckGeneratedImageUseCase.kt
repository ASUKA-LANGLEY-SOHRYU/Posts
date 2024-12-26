package com.example.posts.domain.usecase

import com.example.posts.data.model.Image
import com.example.posts.data.model.ImageResponse
import com.example.posts.domain.repository.GeneratedImageRepository
import io.reactivex.Single

class CheckGeneratedImageUseCase(
    private val generatedImageRepository: GeneratedImageRepository
) {
    fun execute(): Single<ImageResponse> {
        return generatedImageRepository.checkGeneration()
    }
}