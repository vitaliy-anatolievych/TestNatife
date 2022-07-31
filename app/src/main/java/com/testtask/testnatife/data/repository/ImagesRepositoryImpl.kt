package com.testtask.testnatife.data.repository

import com.testtask.testnatife.BuildConfig
import com.testtask.testnatife.core.type.Either
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.core.type.None
import com.testtask.testnatife.data.entity.ImageBlockEntity
import com.testtask.testnatife.data.entity.ImageEntity
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

                    // get black images list
                    val blackList
                    = imagesCache.getBlackList()

                    // doing clean response images list
                    val clearedList
                    = checkListOnBlackListElements(blackList, response.b.toMutableList())

                    // add to local cache
                    imagesCache.addImagesToCache(clearedList)

                    // mapping for domain model
                    val imageModelList =
                        ImagesMapper.mapListImageEntityToListImageModel(clearedList)

                    // return
                    response.right(imageModelList)
                }
            }
        }
    }

    override fun addImageToBlackList(imageModel: ImageModel) : Either<Failure, None> =
        imagesCache.addToBlackList(ImagesMapper.mapImageModelToImageBlockEntity(imageModel))

    private fun checkListOnBlackListElements(
        blackList: List<ImageBlockEntity>,
        imageEntityList: MutableList<ImageEntity>
    ): List<ImageEntity> {
        val clearedList = imageEntityList.toMutableList()
        blackList.forEach { blackListEntity ->
            imageEntityList.forEachIndexed { index, imageModel ->
                if (blackListEntity.id == imageModel.id) {
                    clearedList.removeAt(index)
                }
            }
        }
        return clearedList
    }
}