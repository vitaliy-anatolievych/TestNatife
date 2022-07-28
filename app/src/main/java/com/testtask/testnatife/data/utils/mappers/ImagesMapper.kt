package com.testtask.testnatife.data.utils.mappers

import com.testtask.testnatife.data.entity.ImageEntity
import com.testtask.testnatife.data.network.responses.ImageResponse
import com.testtask.testnatife.domain.models.ImageModel

object ImagesMapper {


    fun mapImageResponseToImageEntity(response: ImageResponse): List<ImageEntity> {
        return mutableListOf<ImageEntity>().apply {
            response.data?.map {
                this.add(
                    ImageEntity(
                        id = it.id,
                        imageUrl = it.images?.original?.url
                    )
                )
            }
        }
    }

    fun mapListImageEntityToListImageModel(imageEntity: List<ImageEntity>): List<ImageModel> {
        return mutableListOf<ImageModel>().apply {
            imageEntity.map {
                if (it.id != null && it.imageUrl != null) {
                    this.add(ImageModel(
                        it.id,
                        it.imageUrl
                    ))
                }
            }
        }
    }
}