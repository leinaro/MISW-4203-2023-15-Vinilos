package com.misw.vinilos

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
            vinilosRepository.getAlbums().collect { albums ->
                setState(
                    state.value.copy(
                        albums = albums,
                    )
                )
            }
        }
    }

    fun setState(newState: VinilosViewState) {
        _state.value = newState
    }
}
