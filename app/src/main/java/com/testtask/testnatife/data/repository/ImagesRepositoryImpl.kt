package com.testtask.testnatife.data.repository

import com.testtask.testnatife.BuildConfig
import com.testtask.testnatife.core.type.Either
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.None
import com.testtask.testnatife.data.local.ImagesCache
import com.testtask.testnatife.data.remote.ImagesRemote
import com.testtask.testnatife.data.utils.mappers.ImagesMapper
import com.testtask.testnatife.domain.models.ImageModel
import com.testtask.testnatife.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesRemote: ImagesRemote,
    private val imagesCache: ImagesCache
): ImagesRepository {

    override fun getImages(
        query: String,
        limit: Int,
        offset: Int,
        rating: String,
        lang: String
    ): Either<Failure, List<ImageModel>> {

        val response = imagesRemote.getImages(
            token = BuildConfig.GIPHY_KEY,
            query = query,
            limit = limit,
            offset = offset,
            rating = rating,
            lang = lang
        )

        return when(response) {
            is Either.Left -> response
            is Either.Right -> {
                return if (response.b.isEmpty()) {
                    Either.Left(Failure.ListEmpty)
                } else {
                    response.right(ImagesMapper.mapListImageEntityToListImageModel(response.b))
                }
            }
        }
    }

    override fun addImageToBlackList(imageModel: ImageModel) : Either<Failure, None> =
        imagesCache.addToBlackList(ImagesMapper.mapImageModelToImageEntity(imageModel))

}