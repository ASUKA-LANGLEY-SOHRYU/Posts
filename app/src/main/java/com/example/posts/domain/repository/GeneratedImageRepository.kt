package com.example.posts.domain.repository

import com.example.posts.data.model.Image
import com.example.posts.data.model.ImageResponse
import io.reactivex.Single

interface GeneratedImageRepository{
    fun generate(prompt: String): Single<String>
    fun checkGeneration(): Single<ImageResponse>
}