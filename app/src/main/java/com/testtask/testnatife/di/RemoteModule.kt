package com.testtask.testnatife.di

import com.testtask.testnatife.core.network.Request
import com.testtask.testnatife.core.network.ServiceFactory
import com.testtask.testnatife.data.remote.ImagesRemote
import com.testtask.testnatife.data.remote.request.ImagesRemoteImpl
import com.testtask.testnatife.data.remote.service.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService =
        ServiceFactory.makeService(false, ApiService::class.java)

    @Singleton
    @Provides
    fun provideImagesRemote(request: Request, apiService: ApiService): ImagesRemote {
        return ImagesRemoteImpl(request, apiService)
    }
}