package com.misw.vinilos

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
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

    @Test
    fun albumTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.misw.vinilos", appContext.packageName)

        assertMainScreenLoaded()
        assertAlbumsScreenLoaded()

        goToCreateAlbum()
        assertCreateAlbumScreen()
        createAlbum()
        assertNewAlbum()
    }

    @Test
    fun artistasTest() {
        composeTestRule.onNodeWithText("Vinilos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Albumes").assertIsDisplayed()
        composeTestRule.onNodeWithText("artistas").assertIsDisplayed()
        composeTestRule.onNodeWithText("artistas").performClick()
        composeTestRule.onNodeWithText("Artistas").assertIsDisplayed()
        Thread.sleep(2000)
        composeTestRule.onAllNodesWithContentDescription("foto artista")[0].assertExists()
        composeTestRule.onAllNodesWithText("Rubén Blades Bellido de Luna")[0].assertIsDisplayed()
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
        }
    }

    private fun goToCreateAlbum(){
        Thread.sleep(1000)
        composeTestRule.onNodeWithContentDescription("Agregar nuevo.").performClick()
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
        composeTestRule.onNodeWithText("Fecha de lanzamiento").performTextInput("2010-11-24")
        composeTestRule.onNodeWithText("Description").performTextInput("Hello Seahorse! broke through Mexico City's competitive indie rock scene with last year's seminal disc 'Bestia.' ")
        composeTestRule.onNodeWithText("Género").performTextInput("Rock")
        composeTestRule.onNodeWithText("Discográfica").performTextInput("Sony Music")
        composeTestRule.onNodeWithTag("CreateAlbumContainer").performScrollToIndex(7)
        composeTestRule.onNodeWithText("Crear").assertIsDisplayed()
    }

    private fun assertNewAlbum(){
        /*Thread.sleep(1000)
        composeTestRule.onNodeWithText("Url de la Imagen").performTextInput("https://f4.bcbits.com/img/a3726590002_65")
        composeTestRule.onNodeWithText("Nombre del Álbum").performTextInput("Lejos no tan lejos")
        composeTestRule.onNodeWithText("Fecha de lanzamiento").performTextInput("2010-11-24")
        composeTestRule.onNodeWithText("Description").performTextInput("Hello Seahorse! broke through Mexico City's competitive indie rock scene with last year's seminal disc 'Bestia.' ")
        composeTestRule.onNodeWithText("Género").performTextInput("Rock")
        composeTestRule.onNodeWithText("Discográfica").performTextInput("Sony Music")
        composeTestRule.onNodeWithTag("CreateAlbumContainer").performScrollToIndex(7)
        composeTestRule.onNodeWithText("Crear").assertIsDisplayed()*/
    }
}