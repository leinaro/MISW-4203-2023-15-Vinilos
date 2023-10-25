package com.misw.vinilos

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onErrorResume
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VinilosViewModel @Inject constructor(
    private val vinilosRepository: VinilosRepository,
) : ViewModel() {


    private val _state = MutableStateFlow(VinilosViewState())
    val state: StateFlow<VinilosViewState> get() = _state

    init {
        viewModelScope.launch {
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
    }

    fun setState(newState: VinilosViewState) {
        _state.value = newState
    }
}
