package com.misw.vinilos.data.repository

import com.misw.vinilos.AlbumsApi
import com.misw.vinilos.data.datasource.api.MusicianApi
import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Musician
import com.misw.vinilos.data.repository.UIError.ServerError
import com.misw.vinilos.domain.VinilosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import java.net.ConnectException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VinilosRepositoryImpl @Inject constructor(
        private val albumsApi: AlbumsApi,
        private val musicianApi: MusicianApi
) : VinilosRepository {

        private val _isRefreshing = MutableStateFlow(false)
        override val isRefreshing: StateFlow<Boolean> = _isRefreshing

        override suspend fun getAlbums(): Flow<List<Album>> {
                return flow {
                        try {
                                _isRefreshing.value = true
                                emit(albumsApi.getAlbums())//.flowOn(Dispatchers.IO)
                        } catch (e: Exception) {
                                _isRefreshing.value = false
                                e.toUiError()
                                //throw e
                        }
                }
        }

        override fun createAlbum(album: Album): Flow<Album> {
                return albumsApi.createAlbum(album)
        }

        override suspend fun getMusicians(): Flow<List<Musician>> {
                return flow {
                        try {
                                _isRefreshing.value = true
                                emit(musicianApi.getMusicians())//.flowOn(Dispatchers.IO)
                        } catch (e: Exception) {
                                _isRefreshing.value = false
                                e.toUiError()
                        }
                }
        }
}

fun Exception.toUiError() {
        print("Error: $this")
        when(this) {
                is ConnectException -> throw UIError.ServerError
                //is SocketException -> throw UIError.ServerError
                else -> throw UIError.UnknownError
        }
}
sealed class UIError(
    val userMessage: String,
): Exception(userMessage) {
        object NetworkError : UIError(userMessage = "Estás en modo ermitaño digital. Sin internet.")
        object ServerError : UIError(userMessage = "¡Ups! El servidor está tomando un café. Intenta nuevamente en breve.")
        object UnknownError : UIError(userMessage = "¡Uh-oh! Algo ha tomado un camino inusual. Llama a nuestro equipo de desarrollo.")
}