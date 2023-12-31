package com.misw.vinilos.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CollectorDao {
    @Query("SELECT * FROM collectorentity ORDER BY name ASC")
    suspend fun getAll(): List<CollectorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collectorEntityList: List<CollectorEntity>)

    @Query("SELECT * FROM collectorentity WHERE id = :collectorId")
    suspend fun get(collectorId: Int?): CollectorEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collectorEntity: CollectorEntity)
}