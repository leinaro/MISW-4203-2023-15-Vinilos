package com.misw.vinilos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.ConnectException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VinilosRepositoryImpl @Inject constructor(
        private val albumsApi: AlbumsApi,
) : VinilosRepository {
        override suspend fun getAlbums(): Flow<List<Album>> {
                return flow {
                        try {
                                emit(albumsApi.getAlbums())//.flowOn(Dispatchers.IO)
                        } catch (e: Exception) {
                                when(e) {
                                        is ConnectException -> throw UIError.ServerError
                                        else -> emit(emptyList<Album>())
                                }
                                print("Error: $e")
                                emit(emptyList<Album>())

                                //throw e
                        }
                }
        }

        override fun createAlbum(album: Album): Flow<Album> {
                return albumsApi.createAlbum(album)
        }
}

sealed class UIError(
    val userMessage: String,
): Exception(userMessage) {
        object NetworkError : UIError(userMessage = "No tienes conexión a internet")
        object ServerError : UIError(userMessage = "No pudimos conectarnos al servidor")
       // object UnknownError : UIError()
}