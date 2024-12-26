package com.example.posts.domain.usecase

import com.example.posts.domain.repository.GeneratedImageRepository
import io.reactivex.Single

class GenerateImageUseCase(
    private val generatedImageRepository: GeneratedImageRepository
) {
    fun execute(prompt: String): Single<String> {
        return generatedImageRepository.generate(prompt)
    }
}