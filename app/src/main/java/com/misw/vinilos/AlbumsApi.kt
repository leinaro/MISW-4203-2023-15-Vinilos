package com.misw.vinilos

import com.misw.vinilos.data.model.Album
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val ALBUMS = "/albums"
interface AlbumsApi {

        @GET(ALBUMS)
        suspend fun getAlbums(): List<Album>


        @POST(ALBUMS)
        fun createAlbum(@Body album: Album): Flow<Album>
}

