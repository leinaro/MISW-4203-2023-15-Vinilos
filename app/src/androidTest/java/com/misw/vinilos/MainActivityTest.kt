package com.misw.vinilos

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import kotlin.concurrent.thread

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

        composeTestRule.onNodeWithText("Vinilos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Albumes").assertIsDisplayed()
        Thread.sleep(2000)
        composeTestRule.onAllNodesWithContentDescription("Buscando América")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Buscando América - Salsa")[0].assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Agregar nuevo.").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Agregar nuevo.").performClick()
        composeTestRule.onNodeWithText("Crear Álbum").assertIsDisplayed()
        composeTestRule.onNodeWithText("Path de la Imagen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nombre del Álbum").assertIsDisplayed()
        composeTestRule.onNodeWithText("Género").assertIsDisplayed()
        composeTestRule.onNodeWithText("Canción").assertIsDisplayed()
        composeTestRule.onNodeWithText("Crear").assertIsDisplayed()
    }

    @Test
    fun artistasTest() {
        composeTestRule.onNodeWithText("Vinilos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Albumes").assertIsDisplayed()
        composeTestRule.onNodeWithText("artistas").assertIsDisplayed()
        composeTestRule.onNodeWithText("artistas").performClick()
        composeTestRule.onNodeWithText("Artistas").assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription("foto artista")[0].assertExists()
        composeTestRule.onAllNodesWithText("Rubén Blades Bellido de Luna")[0].assertIsDisplayed()
    }
}