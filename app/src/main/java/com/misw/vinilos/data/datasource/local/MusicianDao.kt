package com.misw.vinilos.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MusicianDao {
    @Query("SELECT * FROM musicianentity ORDER BY name ASC")
    suspend fun getAll(): List<MusicianEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(musicianEntityList: List<MusicianEntity>)
}