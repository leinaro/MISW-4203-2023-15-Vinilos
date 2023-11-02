package com.misw.vinilos.data.datasource.api

import com.misw.vinilos.data.model.Album
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val albumsApi: AlbumsApi,
    private val musicianApi: MusicianApi,
) {
    suspend fun getAlbums() = albumsApi.getAlbums()
    fun createAlbum(album: Album) = albumsApi.createAlbum(album)
    suspend fun getMusicians() = musicianApi.getMusicians()
}