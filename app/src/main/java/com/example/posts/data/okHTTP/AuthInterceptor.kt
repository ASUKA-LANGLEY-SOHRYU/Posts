package com.example.posts.data.okHTTP

import android.util.Log
import com.example.posts.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.i("Auth inter", request.toString())
        if (!request.url.encodedPath.startsWith("/api/auth")) {
            val authToken = tokenRepository.load() ?: ""
            Log.i("Auth inter", authToken)
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $authToken")
                .build()
            return chain.proceed(newRequest)
        }

        return chain.proceed(request)
    }
}