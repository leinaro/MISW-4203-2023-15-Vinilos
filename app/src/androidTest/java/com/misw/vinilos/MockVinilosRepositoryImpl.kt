package com.misw.vinilos

import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Musician
import com.misw.vinilos.domain.VinilosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private val albumList = listOf(
    Album(
        name = "Buscando América",
        cover = "https://f4.bcbits.com/img/a3726590002_65",
        releaseDate = "2014-01-01",
        description = "Album description",
        genre = "Salsa",
        recordLabel = "Album record label",
    ),
    Album(
        name = "Album name",
        cover = "https://f4.bcbits.com/img/a3726590002_65",
        releaseDate = "2021-01-01",
        description = "Album description",
        genre = "Album genre",
        recordLabel = "Album record label",
    ),
    Album(
        name = "Interstellar",
        cover = "https://f4.bcbits.com/img/a3726590002_65",
        releaseDate = "2014-01-01",
        description = "Album description",
        genre = "Album genre",
        recordLabel = "Album record label",
    ),
)

private val musicianList = listOf(
    Musician(
        id = "1",
        name = "Hans Zimmer",
        image = "https://f4.bcbits.com/img/a3726590002_65",
        birthDate = "1957-01-01",
        description = "Musician description",
    ),
    Musician(
        id = "2",
        name = "Rubén Blades Bellido de Luna",
        image = "https://f4.bcbits.com/img/a3726590002_65",
        birthDate = "1957-01-01",
        description = "Musician description",
    ),
    Musician(
        id = "2",
        name = "Ludwig Göransson",
        image = "https://f4.bcbits.com/img/a3726590002_65",
        birthDate = "1984-01-01",
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
            emit(album)
        }
    }

    override suspend fun getMusicians(): Flow<List<Musician>> {
        return flow {
            emit(musicianList)
        }
    }

    override val isRefreshing: StateFlow<Boolean> = MutableStateFlow(false)
    override val isInternetAvailable: StateFlow<Boolean> = MutableStateFlow(true)
}