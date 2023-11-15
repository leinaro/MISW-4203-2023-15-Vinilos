package com.misw.vinilos.data.datasource.api

import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.data.model.Musician
import retrofit2.http.GET
import retrofit2.http.Path

private const val COLLECTORS = "/collectors"

interface CollectorApi {
    @GET(COLLECTORS)
    suspend fun getCollectors(): List<Collector>

    @GET("$COLLECTORS/{id}")
    suspend fun getCollector(@Path("id") collectorId: Int?): Collector
}