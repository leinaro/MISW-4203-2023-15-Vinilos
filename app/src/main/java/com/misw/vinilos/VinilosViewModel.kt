package com.misw.vinilos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misw.vinilos.domain.VinilosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VinilosViewModel @Inject constructor(
    private val vinilosRepository: VinilosRepository,
) : ViewModel() {


    private val _state = MutableStateFlow(VinilosViewState())
    val state: StateFlow<VinilosViewState> get() = _state
    val isRefreshing: StateFlow<Boolean> = vinilosRepository.isRefreshing

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
}
