package com.example.posts.domain.di

import com.example.posts.domain.provider.PostsRequestProvider
import com.example.posts.domain.usecase.CheckGeneratedImageUseCase
import com.example.posts.domain.usecase.CreatePostUseCase
import com.example.posts.domain.usecase.GenerateImageUseCase
import com.example.posts.domain.usecase.GetMeUseCase
import com.example.posts.domain.usecase.GetMyPostsUseCase
import com.example.posts.domain.usecase.GetRecommendedPostsUseCase
import com.example.posts.domain.usecase.LoginUseCase
import com.example.posts.domain.usecase.RegistrationUseCase
import org.koin.dsl.module

val domainModule = module {
    single { LoginUseCase(
        authRepository = get()
    ) }
    single { RegistrationUseCase(
        authRepository = get()
    ) }

    single { GetRecommendedPostsUseCase(
        postsRepository = get(),
        imageUrlProvider = get()
    ) }
    single { GetMyPostsUseCase(
        postsRepository = get(),
        imageUrlProvider = get()
    ) }
    single { CreatePostUseCase(
        postsRepository = get()
    ) }

    single { GenerateImageUseCase(
        generatedImageRepository = get()
    ) }
    single { CheckGeneratedImageUseCase(
        generatedImageRepository = get()
    ) }

    single { GetMeUseCase(
        userRepository = get()
    ) }
    factory { PostsRequestProvider() }
}