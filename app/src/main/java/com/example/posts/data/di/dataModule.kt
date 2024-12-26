package com.example.posts.data.di

import android.content.Context
import com.example.posts.data.datasource.api.retrofit.PostsApiService
import com.example.posts.data.okHTTP.AuthInterceptor
import com.example.posts.data.okHTTP.CachingController
import com.example.posts.data.okHTTP.UnauthorizedInterceptor
import com.example.posts.data.okHTTP.UnsafeOkHttpClient
import com.example.posts.data.provider.GlideUrlProvider
import com.example.posts.data.provider.ImageUrlProviderImpl
import com.example.posts.data.repository.DefaultAuthRepository
import com.example.posts.data.repository.DefaultGeneratedImageRepository
import com.example.posts.data.repository.DefaultUserRepository
import com.example.posts.data.repository.DefaultPostsRepository
import com.example.posts.data.repository.SharedPrefsTokenRepository
import com.example.posts.domain.provider.ImageUrlProvider
import com.example.posts.domain.repository.AuthRepository
import com.example.posts.domain.repository.GeneratedImageRepository
import com.example.posts.domain.repository.PostsRepository
import com.example.posts.domain.repository.TokenRepository
import com.example.posts.domain.repository.UserRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

const val BASE_URL = "http://192.168.31.107:8087/"

val dataModule = module {
    factory { providePostsApiService(retrofit = get()) }
    single { provideRetrofitInstance(client = get()) }
    single { provideClient(
        cachingControl = get(),
        context = get(),
        authInterceptor = get(),
        unauthorizedInterceptor = get()
    ) }
    single { CachingController(context = get()) }
    single<AuthRepository> { DefaultAuthRepository(
        postsApiService = get()
    ) }
    single<TokenRepository> { SharedPrefsTokenRepository(context = get()) }
    single<UserRepository> { DefaultUserRepository(
        apiService = get()
    ) }
    single<GeneratedImageRepository> { DefaultGeneratedImageRepository(
        apiService = get()
    ) }
    single<PostsRepository> { DefaultPostsRepository(
        apiService = get()
    ) }
    single <ImageUrlProvider> { ImageUrlProviderImpl(
        baseUrl = BASE_URL
    ) }
    single { AuthInterceptor(
        tokenRepository = get()
    ) }
    single { UnauthorizedInterceptor(
        context = get()
    ) }
    single {
        GlideUrlProvider(
            imageUrlProvider = get(),
            tokenRepository = get()
        )
    }
}

private fun provideClient(cachingControl: CachingController,
                          context: Context,
                          authInterceptor: AuthInterceptor,
                          unauthorizedInterceptor: UnauthorizedInterceptor): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val cacheSize = (10 * 1024 * 1024).toLong()
    val cache = Cache(context.cacheDir, cacheSize)

    return UnsafeOkHttpClient.getUnsafeOkHttpClientBuilder()
        .addInterceptor(interceptor)
        .addInterceptor(cachingControl.offlineInterceptor)
        .addInterceptor(authInterceptor)
        .addInterceptor(unauthorizedInterceptor)
        .addNetworkInterceptor(cachingControl.onlineInterceptor)
        //.cache(cache)
        .build()
}

private fun provideRetrofitInstance(client: OkHttpClient): Retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(client)
    .build()

private fun providePostsApiService(retrofit: Retrofit): PostsApiService =
    retrofit.create(PostsApiService::class.java)