package com.testtask.testnatife.presentation.adapters.utils

import com.testtask.testnatife.domain.models.ImageModel
import com.testtask.testnatife.presentation.adapters.models.ImageRVModel

object RVAdapterMapper {

    fun mapListImageModelToListImageRVModel(list: List<ImageModel>): List<ImageRVModel> {
        return mutableListOf<ImageRVModel>().apply {
            list.map {
                this.add(ImageRVModel(
                    id = it.id,
                    imageUrl = it.imageUrl,
                    isSelected = false
                ))
            }
        }
    }
}