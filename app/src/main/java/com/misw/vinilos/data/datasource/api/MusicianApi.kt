package com.misw.vinilos.data.datasource.api

import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Musician
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val MUSICIANS = "/musicians"

interface MusicianApi {
    @GET(MUSICIANS)
    suspend fun getMusicians(): List<Musician>


    @GET("$MUSICIANS/{id}")
    suspend fun getMusician(@Path("id") musicianId: Int?): Musician

    @GET("$MUSICIANS/{id}/albums/")
    suspend fun getAlbumsByMusicianId(@Path("id") musicianId: Int?): List<Album>

    @POST("$MUSICIANS/{musicianId}/albums/{albumId}")
    suspend fun addAlbumToMusician(@Path("musicianId") musicianId: Int?, @Path("albumId") albumId: Int?): Album


}

