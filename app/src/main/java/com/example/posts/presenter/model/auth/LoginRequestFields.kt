package com.example.posts.presenter.model.auth

import com.example.posts.domain.model.auth.AuthRequest

data class LoginRequestFields(
    var username: String = "",
    var password: String = ""
) {
    fun toAuthRequest(): AuthRequest {
        return AuthRequest(username, password)
    }
}
