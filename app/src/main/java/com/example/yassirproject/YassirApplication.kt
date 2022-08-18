package com.example.yassirproject

import android.app.Application
import com.example.yassirproject.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class YassirApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@YassirApplication)
            modules(
                appModule
            )
        }
    }
}