package com.testtask.testnatife.data.remote.request

import com.testtask.testnatife.core.network.Request
import com.testtask.testnatife.core.type.Either
import com.testtask.testnatife.core.type.Failure
import com.testtask.testnatife.data.entity.ImageEntity
import com.testtask.testnatife.data.remote.ImagesRemote
import com.testtask.testnatife.data.remote.service.ApiService
import com.testtask.testnatife.data.utils.mappers.ImagesMapper
import javax.inject.Inject

class ImagesRemoteImpl @Inject constructor(
    private val request: Request,
    private val service: ApiService
) : ImagesRemote {

    override fun getImages(
        token: String,
        query: String,
        limit: Int,
        offset: Int,
        rating: String,
        lang: String
    ): Either<Failure, List<ImageEntity>> {
        return request.make(service.getImages(
            token, query, limit, offset, rating, lang
        )) {
            ImagesMapper.mapImageResponseToImageEntity(it)
        }
    }

}