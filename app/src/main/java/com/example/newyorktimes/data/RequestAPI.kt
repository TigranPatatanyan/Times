package com.example.newyorktimes.data

import com.example.newyorktimes.core.data.DataResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestAPI {
    @GET("viewed/{period}.json?api-key=YNo1eGXsz3d7MSgclh3fSLZZ2wFIbtfP")
    suspend fun getMostPopularNews(@Path("period") period: String): DataResponse<List<ArticlePojo>>
}
