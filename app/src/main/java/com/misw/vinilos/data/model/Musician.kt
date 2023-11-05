package com.misw.vinilos.data.model

import com.misw.vinilos.data.datasource.local.MusicianEntity

data class Musician(
    val id: String,
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

