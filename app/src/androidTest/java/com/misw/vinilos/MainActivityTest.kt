package com.misw.vinilos

import android.R.id
import android.widget.DatePicker
import android.widget.ScrollView
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
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
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.airbnb.lottie.model.LottieCompositionCache
import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.model.Musician
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.hamcrest.core.IsInstanceOf
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

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

    lateinit var idlingResource: LottieIdlingResource

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
    fun albumTest() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(6000)
        composeTestRule.mainClock.autoAdvance = true

        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.misw.vinilos", appContext.packageName)

        assertMainScreenLoaded()
        assertAlbumsScreenLoaded()

        goToCreateAlbum()
        assertCreateAlbumScreen()
        createAlbum()
      //  assertNewAlbum()
    }

    @Test
    fun artistasTest() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(6000)
        composeTestRule.mainClock.autoAdvance = true

        composeTestRule.onNodeWithText("Vinilos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Albumes").assertIsDisplayed()
        composeTestRule.onNodeWithText("artistas").assertIsDisplayed()
        composeTestRule.onNodeWithText("artistas").performClick()

        assertArtisScreenLoaded()
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

    private fun assertCreateAlbumScreen(){
        Thread.sleep(1000)
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
        composeTestRule.onNodeWithText("Nombre del Álbum").performTextInput("Lejos no tan lejos")
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
        composeTestRule.onNodeWithText("Género").performTextInput("Rock")
        composeTestRule.onNodeWithText("Discográfica").performTextInput("Sony Music")
        composeTestRule.onNodeWithTag("CreateAlbumContainer").performScrollToIndex(7)
        composeTestRule.onNodeWithText("Crear").performClick()
        Thread.sleep(10000)
    }

    private fun assertAlbum(album: Album){
        Thread.sleep(1000)
        composeTestRule.onNodeWithContentDescription(album.name).assertIsDisplayed()
        composeTestRule.onNodeWithText("Album: ${album.name}").assertIsDisplayed()
        composeTestRule.onNodeWithText("Genero: ${album.genre}").assertIsDisplayed()
        composeTestRule.onNodeWithText("Año: ${album.releaseDate}").assertIsDisplayed()
        composeTestRule.onNodeWithText(album.description).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }

    private fun assertArtisScreenLoaded() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(6000)
        composeTestRule.mainClock.autoAdvance = true

        Thread.sleep(1000)
        composeTestRule.onNodeWithText("Artistas").assertIsDisplayed()

        musicianList.forEach { musician ->
            composeTestRule.onNodeWithText(musician.name).assertIsDisplayed()
            goToDetailMusician(musician)
        }
        //composeTestRule.onAllNodesWithContentDescription("foto artista")[0].assertExists()
        //composeTestRule.onAllNodesWithText("Rubén Blades Bellido de Luna")[0].assertIsDisplayed()
    }

    private fun goToDetailMusician(musician: Musician){
        composeTestRule.onNodeWithText(musician.name).performClick()
        Thread.sleep(3000)
        assertMusician(musician)
        composeTestRule.onNodeWithContentDescription("Back").performClick()
    }

    private fun assertMusician(musician: Musician){
        Thread.sleep(1000)
        composeTestRule.onNodeWithContentDescription(musician.name).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }
}