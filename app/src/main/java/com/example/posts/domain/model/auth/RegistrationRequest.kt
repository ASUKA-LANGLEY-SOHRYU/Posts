package com.example.posts.domain.model.auth

data class RegistrationRequest(
    val username: String,
    val password: String,
    val email: String
)
