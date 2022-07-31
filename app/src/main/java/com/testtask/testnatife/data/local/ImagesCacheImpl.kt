package com.testtask.testnatife.data.local

import com.testtask.testnatife.core.type.Either
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.None
import com.testtask.testnatife.data.entity.ImageEntity
import com.testtask.testnatife.data.local.storages.room.ImagesStorageDao
import javax.inject.Inject

class ImagesCacheImpl @Inject constructor(
    private val dao: ImagesStorageDao
): ImagesCache {

    override fun addToBlackList(imageEntity: ImageEntity): Either<Failure, None> {
        dao.addImageToBlackList(imageEntity)
        return Either.Right(None())
    }
}