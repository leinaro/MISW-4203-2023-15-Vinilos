package com.misw.vinilos.data.datasource.api

import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.data.model.Musician
import retrofit2.http.GET

private const val COLLECTORS = "/collectors"

interface CollectorApi {
    @GET(COLLECTORS)
    suspend fun getCollectors(): List<Collector>
}