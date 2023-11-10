package com.misw.vinilos.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [
    MusicianEntity::class,
    AlbumEntity::class,
    CollectorEntity::class,
                     ], version = 1, exportSchema = false)
abstract class VinilosDatabase: RoomDatabase() {
    abstract fun musicianDao(): MusicianDao
    abstract fun albumDao(): AlbumDao
    abstract fun collectorDao(): CollectorDao
}