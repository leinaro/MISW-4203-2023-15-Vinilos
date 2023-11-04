package com.misw.vinilos.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albumentity ORDER BY name ASC")
    suspend fun getAll(): List<AlbumEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(albumEntityList: List<AlbumEntity>)
}