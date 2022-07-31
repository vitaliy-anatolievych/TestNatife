package com.testtask.testnatife.data.local

import com.testtask.testnatife.data.entity.ImageEntity

interface ImagesCache {

    fun addToBlackList(imageEntity: ImageEntity)
}