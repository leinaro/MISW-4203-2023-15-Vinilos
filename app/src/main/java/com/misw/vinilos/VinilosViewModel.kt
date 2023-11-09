package com.misw.vinilos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misw.vinilos.data.model.Album
import com.misw.vinilos.VinilosEvent.NavigateBack
import com.misw.vinilos.domain.VinilosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VinilosViewModel @Inject constructor(
    private val vinilosRepository: VinilosRepository,
) : ViewModel() {


    private val _state = MutableStateFlow(VinilosViewState())
    val state: StateFlow<VinilosViewState> get() = _state

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
        }
    }

    private suspend fun getAlbums() {
        vinilosRepository.getAlbums()
            .catch {
                setState(
                    state.value.copy(
                        error = it.message,
                    )
                )
            }
            .collect { albums ->
                setState(
                    state.value.copy(
                        albums = albums,
                        error = null
                    )
                )
            }
    }

    fun getAlbum(albumId: Int) {
        viewModelScope.launch {
            vinilosRepository.getAlbum(albumId)
                .catch {
                    setState(
                        state.value.copy(
                            error = it.message,
                        )
                    )
                }
                .collect { album ->
                    setState(
                        state.value.copy(
                            album = album,
                            error = null
                        )
                    )
                }
        }
    }

    fun setState(newState: VinilosViewState) {
        _state.value = newState
    }


    private suspend fun getMusicians(){
        vinilosRepository.getMusicians()
            .catch {
                setState(
                    state.value.copy(
                        error = it.message,
                    )
                )
            }
            .collect { musicians ->
                setState(
                    state.value.copy(
                        musicians = musicians,
                        error = null
                    )
                )
            }
    }

    fun createAlbum(album: Album) {
        viewModelScope.launch {
            vinilosRepository.createAlbum(album)
                .catch {
                    setState(
                        state.value.copy(
                            error = it.message,
                        )
                    )
                }
                .collect { album ->
                    Log.e("iarl", album.toString())
                    setEvent(NavigateBack)
                    /*setState(
                        state.value.copy(
                            musicians = musicians,
                            error = null
                        )
                    )*/
                }
        }
    }
    fun setEvent(event: VinilosEvent) {
        _event.value = event
    }
}
