package com.misw.vinilos.data.datasource.local

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val musicianDao: MusicianDao,
) {
    suspend fun getMusicians() = musicianDao.getAll().map {
        it.toDto()
    }

    suspend fun insertMusicians(musicianEntityList: List<MusicianEntity>) {
        musicianDao.insert(musicianEntityList)
    }
}