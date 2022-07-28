package com.testtask.testnatife.data.network.responses

import com.testtask.testnatife.data.entity.Images

data class BaseResponse(
    val id: String,
    val images: List<Images>
)