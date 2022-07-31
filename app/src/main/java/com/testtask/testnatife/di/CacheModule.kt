package com.testtask.testnatife.di

import android.app.Application
import com.testtask.testnatife.core.db.AppDataBase
import com.testtask.testnatife.data.local.ImagesCache
import com.testtask.testnatife.data.local.ImagesCacheImpl
import com.testtask.testnatife.data.local.storages.room.ImagesStorageDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun provideImagesCache(dao: ImagesStorageDao): ImagesCache = ImagesCacheImpl(dao)

    @Singleton
    @Provides
    fun providesImagesStorageDao(application: Application): ImagesStorageDao {
        return AppDataBase.getInstance(application).blackListDao()
    }
}