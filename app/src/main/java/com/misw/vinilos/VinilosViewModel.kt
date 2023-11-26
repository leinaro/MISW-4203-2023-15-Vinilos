package com.misw.vinilos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misw.vinilos.VinilosEvent.NavigateTo
import com.misw.vinilos.data.model.Album
import com.misw.vinilos.domain.VinilosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject

@HiltViewModel
class VinilosViewModel @Inject constructor(
    private val vinilosRepository: VinilosRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(VinilosViewState())
    val state: StateFlow<VinilosViewState> get() = _state

    private val mutex = Mutex()

    private val _event: MutableStateFlow<VinilosEvent> = MutableStateFlow(VinilosEvent.Idle)
    val event: StateFlow<VinilosEvent> get() = _event

    val isRefreshing: StateFlow<Boolean> = vinilosRepository.isRefreshing
    var isInternetAvailable: StateFlow<Boolean> = vinilosRepository.isInternetAvailable


    init {
        getAllInformation()
    }

    fun getAllInformation() {
        viewModelScope.launch {
            getMusicians()
            getAlbums()
            getCollectors()
        }
    }

    private suspend fun getAlbums() {
        vinilosRepository.getAlbums()
            .catch {
                setEvent(
                    VinilosEvent.ShowError(it.message ?: "Error al obtener los albums")
                )
            }
            .collect { albums ->
                setState(
                    state.value.copy(
                        albums = albums,
                    )
                )
            }
    }

    fun getAlbum(albumId: Int) {
        viewModelScope.launch {
            vinilosRepository.getAlbum(albumId)
                .catch {
                    setEvent(
                        VinilosEvent.ShowError(it.message ?: "Error al obtener los albumes")
                    )
                }
                .collect { album ->
                    setState(
                        state.value.copy(
                            album = album,
                        )
                    )
                }
        }
    }

    fun getCollector(collectorId: Int) {
        viewModelScope.launch {
            vinilosRepository.getCollector(collectorId)
                .catch {
                    setEvent(
                        VinilosEvent.ShowError(it.message ?: "Error al obtener los coleccionista")
                    )
                }
                .collect { collector ->
                    setState(
                        state.value.copy(
                            collector = collector,
                        )
                    )
                }
        }
    }

    fun getMusician(musicianId: Int) {
        viewModelScope.launch {
            vinilosRepository.getMusician(musicianId)
                .catch {
                    setEvent(
                        VinilosEvent.ShowError(it.message ?: "Error al obtener los artista")
                    )
                }
                .collect { musician ->
                    setState(
                        state.value.copy(
                            musician = musician,
                        )
                    )
                }
        }
    }

    private fun setState(newState: VinilosViewState) {
        _state.value = newState
    }


    private suspend fun getMusicians(){
        vinilosRepository.getMusicians()
            .catch {
                setEvent(
                    VinilosEvent.ShowError(it.message ?: "Error al obtener los musicos")
                )
            }
            .collect { musicians ->
                setState(
                    state.value.copy(
                        musicians = musicians,
                    )
                )
            }
    }

    private suspend fun getCollectors() {
        vinilosRepository.getCollectors()
            .catch {
                setEvent(
                    VinilosEvent.ShowError(it.message ?: "Error al obtener coleccionistas")
                )
            }
            .collect { collectors ->
                setState(
                    state.value.copy(
                        collectors = collectors,
                 )
                )
            }
    }

    fun createAlbum(album: Album) {
        viewModelScope.launch {
            vinilosRepository.createAlbum(album)
                .catch {
                    setEvent(
                        VinilosEvent.ShowError(it.message ?: "Error al crear el album")
                    )
                }
                .collect { album ->
                    setEvent(VinilosEvent.ShowSuccess("Álbum creado con éxito"))
                    delay(1000)
                    setEvent(NavigateTo("album/${album.id}"))
                }
        }
    }
    fun setEvent(event: VinilosEvent) {
        viewModelScope.launch {
            //mutex.withLock {
                _event.value = event
            //}
        }
    }
}


