package com.misw.vinilos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
                                print("Error: $e")
                                throw e
                        }
                }
        }

        override fun createAlbum(album: Album): Flow<Album> {
                return albumsApi.createAlbum(album)
        }
}