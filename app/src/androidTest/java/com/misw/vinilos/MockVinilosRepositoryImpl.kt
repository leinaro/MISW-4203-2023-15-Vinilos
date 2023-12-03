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

val collectorList = listOf(
    Collector(
        name = "Jaime Andrés Monsalve",
        telephone=  "3102178976",
        email= "j.monsalve@gmail.com",
        id =  1
    ),
    Collector(
        name= "María Alejandra Palacios",
        telephone = "3502889087",
        email= "j.palacios@outlook.es",
        id =  2
    )
)

class MockVinilosRepositoryImpl @Inject constructor() : VinilosRepository {
    override  fun getAlbums(): Flow<List<Album>> {
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

    override fun getMusicians(): Flow<List<Musician>> {
        return flow {
            emit(musicianList)
        }
    }

    override fun getCollectors(): Flow<List<Collector>> {
        return flow {
            emit(collectorList)
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
                collectorList.first { collector ->
                    collector.id == collectorId
                }
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

    override fun getAlbumsByMusicianId(musicianId: Int?): Flow<List<Album>> {
        return flow {
            emit(albumList)
        }
    }

    override fun addAlbumToMusician(
        albumId: Int?,
        musicianId: Int?,
    ): Flow<Album> {
        return flow {
            albumList.firstOrNull { album ->
                album.id == albumId
            }?.let { album ->
                album.musicianId = musicianId
                emit(album)
            }
        }
    }

    override val isRefreshing: StateFlow<Boolean> = MutableStateFlow(false)
    override val isInternetAvailable: StateFlow<Boolean> = MutableStateFlow(true)
}