package com.misw.vinilos.data.model

import com.misw.vinilos.data.datasource.local.MusicianEntity
import com.squareup.moshi.Json

data class Musician(
    @Json val id: Int? = null,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
)

fun Musician.toEntity() = MusicianEntity(
    id = id,
    name = name,
    image = image,
    description = description,
    birthDate = birthDate,
)

