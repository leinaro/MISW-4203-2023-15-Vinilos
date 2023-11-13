package com.misw.vinilos.data.datasource.api

import com.misw.vinilos.data.model.Musician
import retrofit2.http.GET

private const val MUSICIANS = "/musicians"

interface MusicianApi {
    @GET(MUSICIANS)
    suspend fun getMusicians(): List<Musician>
}

