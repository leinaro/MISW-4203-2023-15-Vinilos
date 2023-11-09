package com.misw.vinilos.domain

import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Musician
import com.misw.vinilos.data.repository.NetworkStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface VinilosRepository {
    suspend fun getAlbums(): Flow<List<Album>>
    fun createAlbum(album: Album): Flow<Album>
    suspend fun getMusicians(): Flow<List<Musician>>

    fun getAlbum(albumId: Int?): Flow<Album>

    val isRefreshing: StateFlow<Boolean>
    val isInternetAvailable: StateFlow<Boolean>
}
