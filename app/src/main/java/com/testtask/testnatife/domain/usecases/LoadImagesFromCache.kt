package com.testtask.testnatife.domain.usecases

import com.testtask.testnatife.core.interactor.UseCase
import com.testtask.testnatife.core.type.Either
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.domain.models.ImageModel
import com.testtask.testnatife.domain.repository.ImagesRepository
import javax.inject.Inject

class LoadImagesFromCache @Inject constructor(private val repository: ImagesRepository)
    : UseCase<List<ImageModel>, LoadImagesFromCache.Params>() {

    data class Params(
        val query: String
    )

    override suspend fun run(params: Params): Either<Failure, List<ImageModel>> =
        repository.getImagesFromCache(params.query)
}