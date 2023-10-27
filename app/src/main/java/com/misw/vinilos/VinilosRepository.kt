package com.misw.vinilos

import com.misw.vinilos.data.model.Album
import kotlinx.coroutines.flow.Flow

interface VinilosRepository {
    suspend fun getAlbums(): Flow<List<Album>>
    fun createAlbum(album: Album): Flow<Album>
}
