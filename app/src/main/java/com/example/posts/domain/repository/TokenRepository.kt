package com.example.posts.domain.repository

interface TokenRepository {

    fun save(token: String)

    fun load(): String?
}