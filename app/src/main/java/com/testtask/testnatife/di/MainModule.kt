package com.testtask.testnatife.di

import com.testtask.testnatife.data.remote.ImagesRemote
import com.testtask.testnatife.data.repository.ImagesRepositoryImpl
import com.testtask.testnatife.domain.repository.ImagesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule {

    @Provides
    @Singleton
    fun provideImagesRepository(remote: ImagesRemote): ImagesRepository {
        return ImagesRepositoryImpl(remote)
    }
}