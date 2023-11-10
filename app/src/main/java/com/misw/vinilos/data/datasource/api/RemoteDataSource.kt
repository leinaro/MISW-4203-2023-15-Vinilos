package com.misw.vinilos.data.datasource.api

import com.misw.vinilos.data.model.Album
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val albumsApi: AlbumsApi,
    private val musicianApi: MusicianApi,
    private val collectorApi: CollectorApi,
) {
    suspend fun getAlbums() = albumsApi.getAlbums()
    suspend fun createAlbum(album: Album) = albumsApi.createAlbum(album)
    suspend fun getMusicians() = musicianApi.getMusicians()
    suspend fun getAlbum(albumId: Int?) = albumsApi.getAlbum(albumId)
    suspend fun getCollectors() = collectorApi.getCollectors()

}