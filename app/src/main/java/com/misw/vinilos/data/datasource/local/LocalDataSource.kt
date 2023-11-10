package com.misw.vinilos.data.datasource.local

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val musicianDao: MusicianDao,
    private val albumDao: AlbumDao,
    private val collectorDao: CollectorDao,
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

    suspend fun getAlbum(albumId: Int?) = albumDao.getAlbum(albumId).toDto()

    suspend fun insertAlbum(albumEntity: AlbumEntity) {
        albumDao.insertAlbum(albumEntity)
    }

    suspend fun getCollectors() = collectorDao.getAll().map {
        it.toDto()
    }

    suspend fun insertCollectors(collectorEntityList: List<CollectorEntity>) {
        collectorDao.insert(collectorEntityList)
    }
}