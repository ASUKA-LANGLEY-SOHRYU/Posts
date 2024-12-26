package com.example.posts.presenter.model.auth

import com.example.posts.domain.model.auth.RegistrationRequest

data class RegistrationRequestFields(
    var email: String = "",
    var username: String = "",
    var password: String = "",
    var confirmPassword: String = "",
) {
    fun toRegistrationRequest(): RegistrationRequest {
        return RegistrationRequest(username, password, email)
    }
}
