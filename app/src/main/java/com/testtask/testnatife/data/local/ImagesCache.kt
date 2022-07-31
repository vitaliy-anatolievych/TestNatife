package com.testtask.testnatife.data.local

import com.testtask.testnatife.core.type.Either
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.None
import com.testtask.testnatife.data.entity.ImageEntity

interface ImagesCache {

    fun addToBlackList(imageEntity: ImageEntity): Either<Failure, None>
}