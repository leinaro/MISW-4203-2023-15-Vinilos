package com.misw.vinilos

import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.data.model.Musician
import com.misw.vinilos.domain.VinilosRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VinilosViewModelTest(){

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val vinilosRepository: VinilosRepository = mockk(relaxed = true)
    private lateinit var viewModel: VinilosViewModel

    private val albumListFlow = flow {
        emit(
            listOf(
                Album(
                    name = "Album name",
                    cover = "https://f4.bcbits.com/img/a3726590002_65",
                    releaseDate = "2021-01-01",
                    description = "Album description",
                    genre = "Album genre",
                    recordLabel = "Album record label",
                )
            )
        )
    }
    private val musicianListFlow = flow {
        emit(
            listOf(
                Musician(
                    id = 1,
                    name = "Hans Zimmer",
                    image = "https://f4.bcbits.com/img/a3726590002_65",
                    birthDate = "1957-01-01",
                    description = "Musician description",
                )
            )
        )
    }
    private val collectorListFlow = flow {
        emit(
            listOf(
                Collector(
                    id = 1,
                    name = "Collector name",
                    telephone = "+573102178976",
                    email = "j.monsalve@gmail.com"
                )
            )
        )
    }

    @Before
    fun setUp() {
        coEvery { vinilosRepository.isRefreshing } returns MutableStateFlow(false)
        coEvery { vinilosRepository.isInternetAvailable } returns MutableStateFlow(true)
        coEvery { vinilosRepository.getAlbums() } returns flow{ emptyList<Album>() }
        coEvery { vinilosRepository.getMusicians() } returns flow{ emptyList<Musician>() }
        coEvery { vinilosRepository.getCollectors() } returns flow{ emptyList<Collector>()}

        viewModel = VinilosViewModel(vinilosRepository)
    }

    @Test
    fun `test get all information`() = runTest {
        // given
        coEvery { vinilosRepository.getAlbums() } returns albumListFlow
        coEvery { vinilosRepository.getMusicians() } returns musicianListFlow
        coEvery { vinilosRepository.getCollectors() } returns collectorListFlow

        // when
        viewModel.getAllInformation()

        // then
        coVerify(exactly = 1) { vinilosRepository.getAlbums() }
        coVerify(exactly = 1) { vinilosRepository.getMusicians() }
        coVerify(exactly = 1) { vinilosRepository.getCollectors() }
    }
}