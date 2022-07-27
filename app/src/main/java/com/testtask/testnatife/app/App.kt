package com.testtask.testnatife.app

import android.app.Application
import com.testtask.testnatife.di.appModule
import com.testtask.testnatife.di.dataModule
import com.testtask.testnatife.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(
                appModule,
                domainModule,
                dataModule
            ))
        }
    }
}