package com.testtask.testnatife.data.local

import com.testtask.testnatife.core.type.Either
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.None
import com.testtask.testnatife.data.entity.ImageBlockEntity
import com.testtask.testnatife.data.entity.ImageEntity
import com.testtask.testnatife.data.local.storages.room.ImagesStorageDao
import javax.inject.Inject

class ImagesCacheImpl @Inject constructor(
    private val dao: ImagesStorageDao
): ImagesCache {

    override fun addToBlackList(imageBlockEntity: ImageBlockEntity): Either<Failure, None> {
        dao.addImageToBlackList(imageBlockEntity)
        return Either.Right(None())
    }

    override fun addImagesToCache(listImageEntity: List<ImageEntity>) = dao.addImageToCache(listImageEntity)

    override fun getBlackList(): List<ImageBlockEntity> = dao.getBlackList()
}