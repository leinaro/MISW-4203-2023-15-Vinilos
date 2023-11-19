package com.misw.vinilos.data.model

import com.misw.vinilos.data.datasource.local.AlbumEntity
import com.squareup.moshi.Json

data class Album(
    @Json val id: Int? = null,
    @Json val name: String,
    @Json val cover: String,
    @Json val releaseDate: String,
    @Json val description: String,
    @Json val genre: String,
    @Json val recordLabel: String,
)

fun Album.toEntity() = AlbumEntity(
    id = id,
    name = name,
    cover = cover,
    releaseDate = releaseDate,
    description = description,
    genre = genre,
    recordLabel = recordLabel,
    )


