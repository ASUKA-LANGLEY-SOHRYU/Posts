package com.example.posts.data.okHTTP

import okhttp3.Interceptor
import okhttp3.Response
import androidx.navigation.NavController
import androidx.navigation.findNavController
import android.content.Context
import android.app.Activity
import android.util.Log
import com.example.posts.R

class UnauthorizedInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        Log.i("INT", response.code.toString())
        if (response.code == 403 || response.code == 401) {
            navigateToAnotherFragment()
        }

        return response
    }

    private fun navigateToAnotherFragment() {
        if (context is Activity) {
            val navController: NavController = context.findNavController(R.id.containerView)
            navController.navigate(R.id.loginFragment)
        }
    }
}