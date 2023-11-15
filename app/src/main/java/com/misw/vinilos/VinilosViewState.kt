package com.misw.vinilos

import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.data.model.Musician

data class VinilosViewState(
    val albums: List<Album> = emptyList(),
    val musicians: List<Musician> = emptyList(),
    val collectors: List<Collector> = emptyList(),
    val album: Album? = null,
    val collector: Collector? = null,
)

sealed class VinilosEvent{
   data class NavigateTo(val route:String): VinilosEvent()
   object NavigateBack: VinilosEvent()
   data class ShowError(val message: String): VinilosEvent()
   object Idle: VinilosEvent()
}

