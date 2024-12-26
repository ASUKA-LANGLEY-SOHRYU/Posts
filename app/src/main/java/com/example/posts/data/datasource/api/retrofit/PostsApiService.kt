package com.example.posts.data.datasource.api.retrofit

import com.example.posts.data.model.Image
import com.example.posts.data.model.ImageResponse
import com.example.posts.data.model.Post
import com.example.posts.data.model.PostCreationRequest
import com.example.posts.data.model.User
import com.example.posts.domain.model.auth.AuthRequest
import com.example.posts.domain.model.auth.AuthResponse
import com.example.posts.domain.model.auth.RegistrationRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostsApiService {

    @POST("/api/auth/register")
    fun register(
        @Body registrationRequest: RegistrationRequest
    ): Single<AuthResponse>

    @POST("/api/auth/authenticate")
    fun auth(
        @Body authRequest: AuthRequest
    ): Single<AuthResponse>

    @POST("/api/posts")
    fun createPost(
        @Body postCreationRequest: PostCreationRequest
    ): Single<Post>

    @GET("/api/posts/top")
    fun topPosts(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<List<Post>>

    @GET("/api/posts/my")
    fun myPosts(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<List<Post>>

    @GET("/api/users/me")
    fun getMe() : Single<User>

    @GET("/api/images/generate")
    fun generateImage(
        @Query("prompt") prompt: String
    ) : Single<String>

    @GET("/api/images/check")
    fun checkImage() : Single<ImageResponse>
}