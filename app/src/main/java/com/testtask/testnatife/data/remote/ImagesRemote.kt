package com.testtask.testnatife.data.remote

import com.testtask.testnatife.core.type.Either
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.data.entity.ImageEntity

interface ImagesRemote {

    fun getImages(
        token: String,
        query: String,
        limit: Int,
        offset: Int,
        rating: String,
        lang: String
    ): Either<Failure, List<ImageEntity>>
}