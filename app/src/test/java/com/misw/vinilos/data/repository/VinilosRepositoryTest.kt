package com.misw.vinilos.data.repository

import com.misw.vinilos.MainDispatcherRule
import com.misw.vinilos.data.datasource.api.RemoteDataSource
import com.misw.vinilos.data.datasource.local.LocalDataSource
import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.data.model.Musician
import com.misw.vinilos.domain.VinilosRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VinilosRepositoryTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val remoteDataSource: RemoteDataSource = mockk(relaxed = true)
    private val localDataSource: LocalDataSource = mockk(relaxed = true)
    private val networkConnectivityService: NetworkConnectivityServiceImpl = mockk(relaxed = true)


    private lateinit var vinilosRepository: VinilosRepository

    @Before
    fun setUp() {
        every { networkConnectivityService.networkConnection() } returns true

        vinilosRepository = VinilosRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            networkConnectivityService = networkConnectivityService,
        )
    }

    @Test
    fun `test isRefreshing`() {
        // given

        // when
        val response = vinilosRepository.isRefreshing

        // then
        Assert.assertEquals(false, response.value)
    }

    @Test
    fun `test isInternetAvailable`() {
        // given

        // when
        val response = vinilosRepository.isInternetAvailable

        // then
        Assert.assertEquals(true, response.value)
    }

    @Test
    fun `test getAlbums`() = runTest {
        // given
        val localAlbums = emptyList<Album>()
        val remoteAlbums = listOf(
            mockk<Album>(relaxed = true),
            mockk<Album>(relaxed = true),
            mockk<Album>(relaxed = true),
        )
        val flowEmissions = listOf(localAlbums, remoteAlbums)

        coEvery { localDataSource.getAlbums() } returns localAlbums
        coEvery { remoteDataSource.getAlbums() } returns remoteAlbums

        // when
        val response = vinilosRepository.getAlbums().toList()

        // then
        Assert.assertEquals(flowEmissions, response)
        Assert.assertFalse(vinilosRepository.isRefreshing.value)
        coVerify(exactly = 1) { localDataSource.getAlbums() }
        coVerify(exactly = 1) { remoteDataSource.getAlbums() }
        coVerify(exactly = 1) { localDataSource.insertAlbums(any()) }
    }

    @Test
    fun `test createAlbum`() = runTest {
        // given
        val newAlbum = mockk<Album>(relaxed = true)
        coEvery { remoteDataSource.createAlbum(newAlbum) } returns newAlbum

        // when
        val response = vinilosRepository.createAlbum(newAlbum).toList()

        // then
        Assert.assertFalse(vinilosRepository.isRefreshing.value)
        coVerify(exactly = 1) { remoteDataSource.createAlbum(any()) }
        coVerify(exactly = 1) { localDataSource.insertAlbum(any()) }
    }

    @Test
    fun `test getMusicians`() = runTest {
        // given
        val localMusicians = emptyList<Musician>()
        val remoteMusicians = listOf(
            mockk<Musician>(relaxed = true),
            mockk<Musician>(relaxed = true),
            mockk<Musician>(relaxed = true),
        )
        val flowEmissions = listOf(localMusicians, remoteMusicians)

        coEvery { localDataSource.getMusicians() } returns localMusicians
        coEvery { remoteDataSource.getMusicians() } returns remoteMusicians

        // when
        val response = vinilosRepository.getMusicians().toList()

        // then
        Assert.assertEquals(flowEmissions, response)
        Assert.assertFalse(vinilosRepository.isRefreshing.value)
        coVerify(exactly = 1) { localDataSource.getMusicians() }
        coVerify(exactly = 1) { remoteDataSource.getMusicians() }
        coVerify(exactly = 1) { localDataSource.insertMusicians(any()) }
    }

    @Test
    fun `test getAlbum`() = runTest {
        // given
        val albumId = mockk<Int>(relaxed = true)
        val album = mockk<Album>(relaxed = true)

        coEvery { localDataSource.getAlbum(albumId) } returns album
        coEvery { remoteDataSource.getAlbum(albumId) } returns album

        // when
        val response = vinilosRepository.getAlbum(albumId).toList()

        // then
        Assert.assertFalse(vinilosRepository.isRefreshing.value)
        coVerify(exactly = 1) { localDataSource.getAlbum(albumId) }
        coVerify(exactly = 1) { remoteDataSource.getAlbum(albumId) }
        coVerify(exactly = 1) { localDataSource.insertAlbum(any()) }
    }

    @Test
    fun `test getCollectors`() = runTest {
        // given
        val localCollectors = emptyList<Collector>()
        val remoteCollectors = listOf(
            mockk<Collector>(relaxed = true),
            mockk<Collector>(relaxed = true),
            mockk<Collector>(relaxed = true),
        )
        val flowEmissions = listOf(localCollectors, remoteCollectors)

        coEvery { localDataSource.getCollectors() } returns localCollectors
        coEvery { remoteDataSource.getCollectors() } returns remoteCollectors

        // when
        val response = vinilosRepository.getCollectors().toList()

        // then
        Assert.assertEquals(flowEmissions, response)
        Assert.assertFalse(vinilosRepository.isRefreshing.value)
        coVerify(exactly = 1) { localDataSource.getCollectors() }
        coVerify(exactly = 1) { remoteDataSource.getCollectors() }
        coVerify(exactly = 1) { localDataSource.insertCollectors(any()) }

    }
}