package com.misw.vinilos.data.datasource.local

import com.misw.vinilos.data.model.Musician
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

    suspend fun getAlbum(albumId: Int?) = albumDao.get(albumId).toDto()

    suspend fun insertAlbum(albumEntity: AlbumEntity) {
        albumDao.insert(albumEntity)
    }

    suspend fun getCollectors() = collectorDao.getAll().map {
        it.toDto()
    }

    suspend fun insertCollectors(collectorEntityList: List<CollectorEntity>) {
        collectorDao.insert(collectorEntityList)
    }

    suspend fun getCollector(collectorId: Int?) = collectorDao.get(collectorId).toDto()

    suspend fun insertCollector(collectorEntity: CollectorEntity) {
        collectorDao.insert(collectorEntity)
    }

    suspend fun getMusician(musicianId: Int?) = musicianDao.get(musicianId).toDto()

    suspend fun insertMusician(musicianEntity: MusicianEntity) {
        musicianDao.insert(musicianEntity)
    }
}