package com.example.posts.presenter.di

import com.example.posts.domain.usecase.GetMeUseCase
import com.example.posts.presenter.adapter.PostsAdapter
import com.example.posts.presenter.ui.auth.vm.LoginViewModel
import com.example.posts.presenter.ui.auth.vm.RegistrationViewModel
import com.example.posts.presenter.ui.generate.vm.GenerateViewModel
import com.example.posts.presenter.ui.profile.vm.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presenterModule = module {
    viewModel { LoginViewModel(
        loginUseCase = get(),
        tokenRepository = get()
    ) }
    viewModel { RegistrationViewModel(
        registrationUseCase = get(),
        tokenRepository = get()
    ) }
    viewModel { ProfileViewModel(
        postsRequestProvider = get(),
        getMeUseCase = get(),
        getMyPostsUseCase = get(),
        glideUrlProvider = get()
    ) }
    viewModel { GenerateViewModel(
        generateImageUseCase = get(),
        checkGeneratedImageUseCase = get(),
        createPostUseCase = get(),
        glideUrlProvider = get()
    ) }

}