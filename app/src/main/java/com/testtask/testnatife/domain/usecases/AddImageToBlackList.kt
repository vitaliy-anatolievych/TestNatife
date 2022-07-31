package com.testtask.testnatife.domain.usecases

import com.testtask.testnatife.core.interactor.UseCase
import com.testtask.testnatife.core.type.Either
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.None
import com.testtask.testnatife.domain.models.ImageModel
import com.testtask.testnatife.domain.repository.ImagesRepository
import javax.inject.Inject

class AddImageToBlackList @Inject constructor(private val repository: ImagesRepository)
    : UseCase<None, ImageModel>(){

    override suspend fun run(image: ImageModel): Either<Failure, None> =
        repository.addImageToBlackList(image)
}