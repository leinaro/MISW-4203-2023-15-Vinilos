package com.misw.vinilos.data.datasource.local

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val musicianDao: MusicianDao,
    private val albumDao: AlbumDao,
) {
    suspend fun getMusicians() = musicianDao.getAll().map {
        it.toDto()
    }

    suspend fun insertMusicians(musicianEntityList: List<MusicianEntity>) {
        musicianDao.insert(musicianEntityList)
    }

    suspend fun getAlbums() = albumDao.getAll().map {
        it.toDto()
    }

    suspend fun insertAlbums(albumsEntityList: List<AlbumEntity>) {
        albumDao.insert(albumsEntityList)
    }
}