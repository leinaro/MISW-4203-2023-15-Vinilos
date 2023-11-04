package com.misw.vinilos

import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Musician

data class VinilosViewState(
    val albums: List<Album> = emptyList(),
    val musicians: List<Musician> = emptyList(),
    val error: String? = null,
)

sealed class VinilosEvent{
   object NavigateBack: VinilosEvent()
   object ShowError: VinilosEvent()
   object Idle: VinilosEvent()
}
