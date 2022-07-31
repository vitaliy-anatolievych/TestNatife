package com.testtask.testnatife.data.local

import com.testtask.testnatife.core.type.Either
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.None
import com.testtask.testnatife.data.entity.ImageBlockEntity
import com.testtask.testnatife.data.entity.ImageEntity

interface ImagesCache {

    fun addToBlackList(imageBlockEntity: ImageBlockEntity): Either<Failure, None>
    fun addImagesToCache(listImageEntity: List<ImageEntity>)
    fun getBlackList(): List<ImageBlockEntity>
}