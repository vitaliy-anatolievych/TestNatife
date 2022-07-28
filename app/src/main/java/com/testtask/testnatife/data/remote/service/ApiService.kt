package com.testtask.testnatife.data.remote.service


import com.testtask.testnatife.data.network.responses.ImageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        // api version
        private const val VERSION = "v1"

        // methods
        private const val SEARCH = "$VERSION/gifs/search"

        // params
        private const val TOKEN = "api_key"
        private const val QUERY = "q"
        private const val LIMIT = "limit"
        private const val OFFSET = "offset"
        private const val RATING = "rating"
        private const val LANG = "lang"
    }

    @GET(SEARCH)
    fun getImages(
        @Query(TOKEN) token: String,
        @Query(QUERY) query: String,
        @Query(LIMIT) limit: Int,
        @Query(OFFSET) offset: Int,
        @Query(RATING) rating: String,
        @Query(LANG) lang: String
    ): Call<ImageResponse>
}