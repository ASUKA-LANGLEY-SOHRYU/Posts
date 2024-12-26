package com.example.posts.app

import android.app.Application
import com.example.posts.data.di.dataModule
import com.example.posts.domain.di.domainModule
import com.example.posts.presenter.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(presenterModule, domainModule, dataModule))
        }
    }
}