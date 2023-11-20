package com.misw.vinilos.data.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.misw.vinilos.data.model.Musician

@Entity
data class MusicianEntity(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name ="name")val name: String,
    @ColumnInfo(name ="image")val image: String,
    @ColumnInfo(name ="description")val description: String,
    @ColumnInfo(name ="birth_date")val birthDate: String,
)

fun MusicianEntity.toDto() = Musician(
    id = id,
    name = name,
    image = image,
    description = description,
    birthDate = birthDate
)

fun List<MusicianEntity>.toDto() = map { it.toDto() }

