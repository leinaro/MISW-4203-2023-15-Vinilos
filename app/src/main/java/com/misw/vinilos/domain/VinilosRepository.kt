package com.misw.vinilos.domain

import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Musician
import kotlinx.coroutines.flow.Flow

interface VinilosRepository {
    suspend fun getAlbums(): Flow<List<Album>>
    fun createAlbum(album: Album): Flow<Album>
    suspend fun getMusicians(): Flow<List<Musician>>
}
