package com.example.tartufozon.network

import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.network.model.TruffleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TruffleService {

    @GET("tartufi/")
    suspend fun getTruffleList(): Response<List<TruffleDto>>

    @GET("tartufi/{id}")
    suspend fun getTruffleDetail(@Path("id") id: Int): Response<TruffleDto>

    // TODO: 21.04.21
    @GET("tartufi")
    suspend fun getTrufflesByQuery(
        @Query("query") query: String
    ): Response<List<TruffleDto>>

}
