package com.misw.vinilos

import android.R.id
import android.widget.ScrollView
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.airbnb.lottie.model.LottieCompositionCache
import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.data.model.Musician
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var idlingResource: LottieIdlingResource

    @Before
    fun setup() {
        LottieCompositionCache.getInstance().clear()
        idlingResource = LottieIdlingResource()
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun albumCreateTest() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(6000)
        composeTestRule.mainClock.autoAdvance = true

        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.misw.vinilos", appContext.packageName)

        assertMainScreenLoaded()

        goToCreateAlbum()
        assertCreateAlbumScreen()
        assertCreateAlbumErrorsScreen()
        createAlbum()
        //  assertNewAlbum()
    }

    @Test
    fun albumTest() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(6000)
        composeTestRule.mainClock.autoAdvance = true

        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.misw.vinilos", appContext.packageName)

        assertMainScreenLoaded()
        assertAlbumsScreenLoaded()
    }

    @Test
    fun artistasTest() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(6000)
        composeTestRule.mainClock.autoAdvance = true

        assertMainScreenLoaded()

        composeTestRule.onNodeWithText("artistas").performClick()


        assertArtistScreenLoaded()
    }

    @Test
    fun colleccionistasTest() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(6000)
        composeTestRule.mainClock.autoAdvance = true

        composeTestRule.onNodeWithText("Vinilos").assertIsDisplayed()
        assertMainScreenLoaded()

        composeTestRule.onNodeWithText("coleccionistas").performClick()

        assertCollectorScreenLoaded()
    }

    private fun assertMainScreenLoaded(){
        Thread.sleep(1000)
        composeTestRule.onNodeWithText("Vinilos").assertIsDisplayed()
        composeTestRule.onNodeWithText("albums").assertIsDisplayed()
        composeTestRule.onNodeWithText("artistas").assertIsDisplayed()
        composeTestRule.onNodeWithText("coleccionistas").assertIsDisplayed()
    }

    private fun assertAlbumsScreenLoaded() {
        Thread.sleep(1000)
        composeTestRule.onNodeWithText("Albumes").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Agregar nuevo.").assertIsDisplayed()
        albumList.forEach { album ->
            composeTestRule.onNodeWithContentDescription(album.name).assertIsDisplayed()
            composeTestRule.onNodeWithText("${album.name} - ${album.genre}").assertIsDisplayed()
            goToDetailAlbum(album)
        }
    }

    private fun goToCreateAlbum(){
        Thread.sleep(1000)
        composeTestRule.onNodeWithContentDescription("Agregar nuevo.").performClick()
    }

    private fun goToDetailAlbum(album: Album){
        composeTestRule.onNodeWithContentDescription(album.name).performClick()
        Thread.sleep(3000)
        assertAlbum(album)
        composeTestRule.onNodeWithContentDescription("Back").performClick()
    }

    private fun assertCreateAlbumErrorsScreen(){
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag("CreateAlbumContainer").performScrollToIndex(7)
        composeTestRule.onNodeWithText("Crear").performClick()
        composeTestRule.onNodeWithTag("CreateAlbumContainer").performScrollToIndex(1)
        Thread.sleep(2000)

        composeTestRule.onNodeWithText("La url no puede estar vacía").assertIsDisplayed()
        composeTestRule.onNodeWithText("El nombre no puede estar vacío").assertIsDisplayed()
        composeTestRule.onNodeWithText("La fecha de lanzamiento no no puede estar vacía").assertIsDisplayed()
        composeTestRule.onNodeWithTag("CreateAlbumContainer").performScrollToIndex(3)
        composeTestRule.onNodeWithText("La descripción no puede estar vacío").assertIsDisplayed()
        composeTestRule.onNodeWithText("El genero no puede estar vacío").assertIsDisplayed()
        composeTestRule.onNodeWithText("La discografica no puede estar vacía").assertIsDisplayed()
    }

    private fun assertCreateAlbumScreen(){
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag("CreateAlbumContainer").performScrollToIndex(1)
        composeTestRule.onNodeWithText("Crear Álbum").assertIsDisplayed()
        composeTestRule.onNodeWithText("Url de la Imagen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nombre del Álbum").assertIsDisplayed()
        composeTestRule.onNodeWithText("Fecha de lanzamiento").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Género").assertIsDisplayed()
        composeTestRule.onNodeWithText("Discográfica").assertIsDisplayed()
        composeTestRule.onNodeWithTag("CreateAlbumContainer").performScrollToIndex(7)
        composeTestRule.onNodeWithText("Crear").assertIsDisplayed()
    }

    private fun createAlbum(){
        Thread.sleep(1000)
        composeTestRule.onNodeWithText("Url de la Imagen").performTextInput("https://f4.bcbits.com/img/a3726590002_65")
        composeTestRule.onNodeWithText("https://f4.bcbits.com/img/a3726590002_65").assertIsDisplayed()

        composeTestRule.onNodeWithText("Nombre del Álbum").performTextInput("Lejos no tan lejos")
        composeTestRule.onNodeWithText("Lejos no tan lejos").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Seleccionar fecha.").performClick()
        val datePickerButton = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(id.button1), ViewMatchers.withText("OK"),
                ViewMatchers.withParent(
                    ViewMatchers.withParent(IsInstanceOf.instanceOf(ScrollView::class.java))
                ),
                ViewMatchers.isDisplayed()
            )
        )
        datePickerButton.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        datePickerButton.perform(click())

        composeTestRule.onNodeWithText("Description").performTextInput("Hello Seahorse! broke through Mexico City's competitive indie rock scene with last year's seminal disc 'Bestia.' ")
        composeTestRule.onNodeWithText("Hello Seahorse! broke through Mexico City's competitive indie rock scene with last year's seminal disc 'Bestia.' ").assertIsDisplayed()

        composeTestRule.onNodeWithTag("CreateAlbumContainer").performScrollToIndex(4)
        composeTestRule.onNodeWithTag("genre-field").performClick()
        composeTestRule.onNodeWithText("Rock").performClick()
        composeTestRule.onNodeWithText("Rock").assertIsDisplayed()

        composeTestRule.onNodeWithTag("CreateAlbumContainer").performScrollToIndex(6)
        composeTestRule.onNodeWithTag("record-field").performClick()
        composeTestRule.onNodeWithText("Sony Music").performClick()
        composeTestRule.onNodeWithText("Sony Music").assertIsDisplayed()

        composeTestRule.onNodeWithTag("CreateAlbumContainer").performScrollToIndex(7)
        composeTestRule.onNodeWithText("Crear").performClick()
        composeTestRule.onNodeWithText("Álbum creado con éxito").assertIsDisplayed()
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(6000)
        composeTestRule.mainClock.autoAdvance = true
     //   composeTestRule.onNodeWithTag("AlbumDetail").assertIsDisplayed()
    //assertAlbum(albumList.last())
    }

    private fun assertAlbum(album: Album){
        Thread.sleep(2000)
//        composeTestRule.onNodeWithContentDescription(album.name).assertIsDisplayed()
        composeTestRule.onNodeWithText("Album: ${album.name}").assertIsDisplayed()
        composeTestRule.onNodeWithText("Genero: ${album.genre}").assertIsDisplayed()
        composeTestRule.onNodeWithText("Año: ${album.releaseDate}").assertIsDisplayed()
        composeTestRule.onNodeWithText(album.description).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }

    private fun assertArtistScreenLoaded() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(6000)
        composeTestRule.mainClock.autoAdvance = true

        Thread.sleep(1000)
        composeTestRule.onNodeWithText("Artistas").assertIsDisplayed()

        musicianList.forEach { musician ->
            composeTestRule.onNodeWithText(musician.name).assertIsDisplayed()
            goToDetailMusician(musician)
        }
    }

    private fun goToDetailMusician(musician: Musician){
        composeTestRule.onNodeWithText(musician.name).performClick()
        Thread.sleep(1000)
        assertMusician(musician)
        composeTestRule.onNodeWithContentDescription("Back").performClick()
    }

    private fun goToDetailCollector(collector: Collector){
        composeTestRule.onNodeWithText(collector.name).performClick()
        Thread.sleep(1000)
        assertCollector(collector)
        Thread.sleep(1000)
        composeTestRule.onNodeWithContentDescription("Back").performClick()
    }

    private fun assertMusician(musician: Musician){
        Thread.sleep(1000)
        composeTestRule.onNodeWithContentDescription(musician.name).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }

    private fun assertCollector(collector: Collector){
        Thread.sleep(1000)
        composeTestRule.onNodeWithText(collector.name).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }

    private fun assertCollectorScreenLoaded() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(6000)
        composeTestRule.mainClock.autoAdvance = true

        Thread.sleep(1000)
        composeTestRule.onNodeWithText("Coleccionistas").assertIsDisplayed()

        collectorList.forEach { collector ->
            composeTestRule.onNodeWithText(collector.name).assertIsDisplayed()
            goToDetailCollector(collector)
        }
    }
}