package com.testtask.testnatife.domain.repository

import com.testtask.testnatife.core.type.Either
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.None
import com.testtask.testnatife.domain.models.ImageModel

interface ImagesRepository {


    fun getImages(
        query: String,
        limit: Int,
        offset: Int,
        rating: String,
        lang: String
    ): Either<Failure, List<ImageModel>>


    fun addImageToBlackList(imageModel: ImageModel): Either<Failure, None>
}