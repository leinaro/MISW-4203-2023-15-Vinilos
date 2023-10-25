package com.misw.vinilos

data class VinilosViewState(
    val albums: List<Album> = emptyList(),
    val error: String? = null,
)
