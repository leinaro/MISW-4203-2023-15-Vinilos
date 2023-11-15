package com.misw.vinilos.data.datasource.api

import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.data.model.Musician
import retrofit2.http.GET
import retrofit2.http.Path

private const val MUSICIANS = "/musicians"

interface MusicianApi {
    @GET(MUSICIANS)
    suspend fun getMusicians(): List<Musician>


    @GET("$MUSICIANS/{id}")
    suspend fun getMusician(@Path("id") musicianId: Int?): Musician
}

