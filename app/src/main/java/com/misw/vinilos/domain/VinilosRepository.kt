package com.misw.vinilos.domain

import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.data.model.Musician
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface VinilosRepository {
    fun getAlbums(): Flow<List<Album>>
    fun createAlbum(album: Album): Flow<Album>
    fun getMusicians(): Flow<List<Musician>>
    fun getCollectors(): Flow<List<Collector>>

    fun getAlbum(albumId: Int?): Flow<Album>
    fun getCollector(collectorId: Int?): Flow<Collector>
    fun getMusician(musicianId: Int?): Flow<Musician>
    fun getAlbumsByMusicianId(musicianId: Int?): Flow<List<Album>>
    fun addAlbumToMusician(albumId: Int?, musicianId: Int?): Flow<Album>

    val isRefreshing: StateFlow<Boolean>
    val isInternetAvailable: StateFlow<Boolean>
}
