package com.misw.vinilos.data.model

import com.misw.vinilos.data.datasource.local.CollectorEntity

data class Collector (
    val id: Int? = null,
    val name: String,
    val telephone: String,
    val email: String,
//    val albums: List<Album>,
)

fun Collector.toEntity() = CollectorEntity(
    id = id,
    name = name,
    telephone = telephone,
    email = email,
    )

