package com.misw.vinilos.data.repository

import com.misw.vinilos.data.datasource.api.RemoteDataSource
import com.misw.vinilos.data.datasource.local.LocalDataSource
import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Musician
import com.misw.vinilos.data.model.toEntity
import com.misw.vinilos.domain.VinilosRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VinilosRepositoryImpl @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val networkConnectivityService: NetworkConnectivityServiceImpl,
) : VinilosRepository {

        private val _isRefreshing = MutableStateFlow(false)
        override val isRefreshing: StateFlow<Boolean> = _isRefreshing

        private val _isInternetAvailable = MutableStateFlow(true)
        override val isInternetAvailable: StateFlow<Boolean> = _isInternetAvailable

        init {
                _isInternetAvailable.value = networkConnectivityService.networkConnection()
                GlobalScope.launch{
                    networkConnectivityService.networkStatus
                        .collect {
                                when(it) {
                                        NetworkStatus.Unknown -> _isInternetAvailable.value = true
                                        NetworkStatus.Connected -> _isInternetAvailable.value = true
                                        NetworkStatus.Disconnected -> _isInternetAvailable.value = false
                                }
                        }
                }
        }

        override suspend fun getAlbums(): Flow<List<Album>> {
                return flow {
                        try {
                                _isRefreshing.value = true
                                emit(localDataSource.getAlbums())
                                val albums = remoteDataSource.getAlbums()
                                emit(albums)
                                val albumsEntityList = albums.map { it.toEntity() }
                                localDataSource.insertAlbums(albumsEntityList)
                                _isRefreshing.value = false
                        } catch (e: Exception) {
                                _isRefreshing.value = false
                                e.toUiError()
                        }
                }
        }

        override fun createAlbum(album: Album): Flow<Album> {
                return flow {
                        try {
                                _isRefreshing.value = true
                                emit(remoteDataSource.createAlbum(album))
                                _isRefreshing.value = false
                        } catch (e: Exception) {
                                e.printStackTrace()
                                _isRefreshing.value = false
                                e.toUiError()
                        }
                }
        }

        override suspend fun getMusicians(): Flow<List<Musician>> {
                return flow {
                        try {
                                _isRefreshing.value = true
                                emit(localDataSource.getMusicians())
                                val musicians = remoteDataSource.getMusicians()
                                emit(musicians)
                                val musicianEntityList = musicians.map { it.toEntity() }
                                localDataSource.insertMusicians(musicianEntityList)
                                _isRefreshing.value = false
                        } catch (e: Exception) {
                                _isRefreshing.value = false
                                e.toUiError()
                        }
                }
        }
}

fun Exception.toUiError() {
        this.printStackTrace()
        when(this) {
                is ConnectException -> throw UIError.NetworkError
                is SocketException -> throw UIError.ServerError
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