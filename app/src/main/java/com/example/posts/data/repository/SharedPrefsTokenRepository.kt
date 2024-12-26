package com.example.posts.data.repository

import android.content.Context
import com.example.posts.domain.repository.TokenRepository

class SharedPrefsTokenRepository(
    val context: Context
) : TokenRepository {
    companion object {
        private const val NAME = "prefs"
        private const val JWT_KEY = "jwt_token"
    }
    override fun save(token: String) {
        val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(JWT_KEY, token).apply()
    }

    override fun load(): String? {
        val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(JWT_KEY, null)
    }
}