package com.misw.vinilos

import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.data.model.Musician
import com.misw.vinilos.domain.VinilosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

val albumList = mutableListOf(
    Album(
        id = 1,
        name = "Buscando América",
        cover = "https://f4.bcbits.com/img/a3726590002_65",
        releaseDate = "2014-01-01",
        description = "Album description",
        genre = "Salsa",
        recordLabel = "Album record label",
    ),
    Album(
        id = 2,
        name = "Album name",
        cover = "https://f4.bcbits.com/img/a3726590002_65",
        releaseDate = "2021-01-01",
        description = "Album description",
        genre = "Album genre",
        recordLabel = "Album record label",
    ),
    Album(
        id = 3,
        name = "Interstellar",
        cover = "https://f4.bcbits.com/img/a3726590002_65",
        releaseDate = "2014-01-01",
        description = "Album description",
        genre = "Album genre",
        recordLabel = "Album record label",
    ),
)

val musicianList = listOf(
    Musician(
        id = 1,
        name = "Hans Zimmer",
        image = "https://f4.bcbits.com/img/a3726590002_65",
        birthDate = "1943-12-27T05:00:00.000Z",
        description = "Musician description",
    ),
    Musician(
        id = 2,
        name = "Rubén Blades Bellido de Luna",
        image = "https://f4.bcbits.com/img/a3726590002_65",
        birthDate = "1943-12-27T05:00:00.000Z",
        description = "Musician description",
    ),
    Musician(
        id = 3,
        name = "Ludwig Göransson",
        image = "https://f4.bcbits.com/img/a3726590002_65",
        birthDate = "1943-12-27T05:00:00.000Z",
        description = "Musician description",
    ),
)

class MockVinilosRepositoryImpl @Inject constructor() : VinilosRepository {
    override suspend fun getAlbums(): Flow<List<Album>> {
        return flow {
            emit(albumList)
        }
    }

    override fun createAlbum(album: Album): Flow<Album> {
        return flow {
            val newAlbum = album.copy(id = 3)
            albumList.add(newAlbum)
            emit(newAlbum)
        }
    }

    override suspend fun getMusicians(): Flow<List<Musician>> {
        return flow {
            emit(musicianList)
        }
    }

    override suspend fun getCollectors(): Flow<List<Collector>> {
        return flow {
            emit(emptyList())
        }
    }

    override fun getAlbum(albumId: Int?): Flow<Album> {
        return flow {
            emit(
                albumList.first { album ->
                    album.id == albumId
                                },
            )
        }
    }

    override fun getCollector(collectorId: Int?): Flow<Collector> {
        return flow {
            emit(
                Collector(
                    id = 1,
                    name = "Collector name",
                    telephone = "+573102178976",
                    email = "j.monsalve@gmail.com"
                )
            )
        }
    }

    override fun getMusician(musicianId: Int?): Flow<Musician> {
        return flow {
            emit(
                musicianList.first { musician ->
                    musician.id == musicianId
                }
            )
        }
    }

    override val isRefreshing: StateFlow<Boolean> = MutableStateFlow(false)
    override val isInternetAvailable: StateFlow<Boolean> = MutableStateFlow(true)
}