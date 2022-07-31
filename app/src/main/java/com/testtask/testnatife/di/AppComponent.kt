package com.testtask.testnatife.di

import android.app.Application
import com.testtask.testnatife.presentation.core.BaseFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [
        ViewModelModule::class,
        MainModule::class,
        RemoteModule::class,
        CacheModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: BaseFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}