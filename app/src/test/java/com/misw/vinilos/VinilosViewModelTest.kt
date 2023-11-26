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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.internal.wait
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VinilosViewModelTest {

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

    private val albumFlow = flow {
        emit(
            Album(
                    name = "Album name",
                    cover = "https://f4.bcbits.com/img/a3726590002_65",
                    releaseDate = "2021-01-01",
                    description = "Album description",
                    genre = "Album genre",
                    recordLabel = "Album record label",
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

    private val musicianFlow = flow {
        emit(
            Musician(
                id = 1,
                name = "Hans Zimmer",
                image = "https://f4.bcbits.com/img/a3726590002_65",
                birthDate = "1957-01-01",
                description = "Musician description",
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

    private val collectorFlow = flow {
        emit(
            Collector(
                id = 1,
                name = "Collector name",
                telephone = "+573102178976",
                email = "j.monsalve@gmail.com"
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

    @Test
    fun `test get album`() = runTest {
        // given
        coEvery { vinilosRepository.getAlbum(1) } returns albumFlow

        // when
        viewModel.getAlbum(1)
        this.testScheduler.advanceUntilIdle()
        // then
        coVerify(exactly = 1) { vinilosRepository.getAlbum(1) }
    }

    @Test
    fun `test get Collector`() = runTest {
        // given
        coEvery { vinilosRepository.getCollector(1) } returns collectorFlow

        // when
        viewModel.getCollector(1)
        this.testScheduler.advanceUntilIdle()
        // then
        coVerify(exactly = 1) { vinilosRepository.getCollector(1) }
    }

    @Test
    fun `test get Musician`() = runTest {
        // given
        coEvery { vinilosRepository.getMusician(1) } returns musicianFlow

        // when
        viewModel.getMusician(1)
        this.testScheduler.advanceUntilIdle()
        // then
        coVerify(exactly = 1) { vinilosRepository.getMusician(1) }
    }

    @Test
    fun `test get Musician error handling with message`() = runTest {
        // given
        coEvery { vinilosRepository.getMusician(1) } returns flow { throw Exception("test") }

        // when
        viewModel.getMusician(1)
        this.testScheduler.advanceUntilIdle()
        // then
        coVerify(exactly = 1) { vinilosRepository.getMusician(1) }
        Assert.assertTrue(viewModel.event.value is VinilosEvent.ShowError)
        val error = viewModel.event.value as VinilosEvent.ShowError
        Assert.assertEquals("test",error.message)
    }

    @Test
    fun `test get Musician error handling without message`() = runTest {
        // given
        coEvery { vinilosRepository.getMusician(1) } returns flow { throw Exception() }

        // when
        viewModel.getMusician(1)
        this.testScheduler.advanceUntilIdle()
        // then
        coVerify(exactly = 1) { vinilosRepository.getMusician(1) }
        Assert.assertTrue(viewModel.event.value is VinilosEvent.ShowError)
        val error = viewModel.event.value as VinilosEvent.ShowError
        Assert.assertEquals("Error al obtener los artista",error.message)
    }

    @Test
    fun `test get Album error handling without message`() = runTest {
        // given
        coEvery { vinilosRepository.getAlbum(1) } returns flow { throw Exception() }

        // when
        viewModel.getAlbum(1)
        this.testScheduler.advanceUntilIdle()
        // then
        coVerify(exactly = 1) { vinilosRepository.getAlbum(1) }
        Assert.assertTrue(viewModel.event.value is VinilosEvent.ShowError)
        val error = viewModel.event.value as VinilosEvent.ShowError
        Assert.assertEquals("Error al obtener los albumes",error.message)
    }

    @Test
    fun `test get Collector error handling without message`() = runTest {
        // given
        coEvery { vinilosRepository.getCollector(1) } returns flow { throw Exception() }

        // when
        viewModel.getCollector(1)
        this.testScheduler.advanceUntilIdle()
        // then
        coVerify(exactly = 1) { vinilosRepository.getCollector(1) }
        Assert.assertTrue(viewModel.event.value is VinilosEvent.ShowError)
        val error = viewModel.event.value as VinilosEvent.ShowError
        Assert.assertEquals("Error al obtener los coleccionista",error.message)
    }

    @Test
    fun `test create album`() = runTest {
        // given
        val album = mockk<Album>(relaxed = true)
        coEvery { vinilosRepository.createAlbum(album) } returns albumFlow

        // when
        viewModel.createAlbum(album)
        this.testScheduler.advanceUntilIdle()

        // then
        coVerify(exactly = 1) { vinilosRepository.createAlbum(any()) }
        Assert.assertTrue(viewModel.event.value is VinilosEvent.NavigateTo)
    }

    @Test
    fun `test create album handle error message`() = runTest {
        // given
        val album = mockk<Album>(relaxed = true)
        coEvery { vinilosRepository.createAlbum(album) } returns flow { throw Exception() }

        // when
        viewModel.createAlbum(album)
        this.testScheduler.advanceUntilIdle()

        // then
        coVerify(exactly = 1) { vinilosRepository.createAlbum(any()) }
        Assert.assertTrue(viewModel.event.value is VinilosEvent.ShowError)
    }



    @Test
    fun `test get all information with Album Error`() = runTest {

        // given
        coEvery { vinilosRepository.getAlbums() } returns flow { throw Exception() }
        coEvery { vinilosRepository.getMusicians() } returns musicianListFlow
        coEvery { vinilosRepository.getCollectors() } returns collectorListFlow

        // when
        viewModel.getAllInformation()
        this.testScheduler.advanceUntilIdle()
        // then
        coVerify(exactly = 2) { vinilosRepository.getAlbums() }
        coVerify(exactly = 2) { vinilosRepository.getMusicians() }
        coVerify(exactly = 2) { vinilosRepository.getCollectors() }
        println(viewModel.event.value)
        Assert.assertTrue(viewModel.event.value is VinilosEvent.ShowError)

    }



}