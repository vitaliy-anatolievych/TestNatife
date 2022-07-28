package com.testtask.testnatife.data.utils.mappers

import com.testtask.testnatife.data.entity.ImageEntity
import com.testtask.testnatife.data.network.responses.ImageResponse

object ImagesMapper {


    fun mapImageResponseToImageEntity(response: ImageResponse): List<ImageEntity> {
        return mutableListOf<ImageEntity>().apply {
            response.images.map {
                this.add(
                    ImageEntity(
                        id = response.id,
                        imageUrl = it.original.url
                    )
                )
            }
        }

    }
}