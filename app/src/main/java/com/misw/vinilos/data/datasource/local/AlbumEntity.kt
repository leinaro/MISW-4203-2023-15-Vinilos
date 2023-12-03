package com.misw.vinilos.data.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.misw.vinilos.data.model.Album

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MusicianEntity::class,
            parentColumns = ["id"],
            childColumns = ["musician_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AlbumEntity(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name ="name") val name: String,
    @ColumnInfo(name ="cover") val cover: String,
    @ColumnInfo(name ="release_date") val releaseDate: String,
    @ColumnInfo(name ="description") val description: String,
    @ColumnInfo(name ="genre") val genre: String,
    @ColumnInfo(name ="record_label") val recordLabel: String,
    @ColumnInfo(name = "musician_id") val musicianId: Int?,
)


fun AlbumEntity.toDto() = Album(
    id = id,
    name = name,
    cover = cover,
    releaseDate = releaseDate,
    description = description,
    genre = genre,
    recordLabel = recordLabel,
    musicianId = musicianId
)

fun List<AlbumEntity>.toDto() = map { it.toDto() }