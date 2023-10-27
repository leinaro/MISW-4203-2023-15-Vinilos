package com.misw.vinilos

import com.misw.vinilos.data.model.Album

data class VinilosViewState(
    val albums: List<Album> = emptyList(),
    val error: String? = null,
)
