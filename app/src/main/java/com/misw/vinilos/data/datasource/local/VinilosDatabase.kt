package com.misw.vinilos.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MusicianEntity::class], version = 1, exportSchema = false)
abstract class VinilosDatabase: RoomDatabase() {
    abstract fun musicianDao(): MusicianDao
}