package com.testtask.testnatife.data.network.responces

import com.testtask.testnatife.data.entity.Images

data class BaseResponse(
    val id: String,
    val images: List<Images>
)