package com.misw.vinilos.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.misw.vinilos.data.model.Musician

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albumentity ORDER BY name ASC")
    suspend fun getAll(): List<AlbumEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(albumEntityList: List<AlbumEntity>)

    @Query("SELECT * FROM albumentity WHERE id = :albumId")
    suspend fun get(albumId: Int?): AlbumEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(albumEntity: AlbumEntity)

    @Query("SELECT * FROM albumentity WHERE musician_id = :musicianId")
    suspend fun getAllAlbumsByMusician(musicianId: Int?): List<AlbumEntity>

    @Update
    suspend fun update(albumEntity: AlbumEntity): Int

}