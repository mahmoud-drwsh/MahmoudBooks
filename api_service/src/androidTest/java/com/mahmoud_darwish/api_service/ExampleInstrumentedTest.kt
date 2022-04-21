package com.mahmoud_darwish.api_service

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.mahmoud_darwish.api_service.test", appContext.packageName)
    }

    @Test
    fun apiReturnsANonEmptyListOfResults() {
        runBlocking {
            val search = getInstance().search("kotlin")

            // assert the Volumes list is not empty
            assertTrue(search.Volumes.isNotEmpty())

            // assert all books have titles
            assertTrue(search.Volumes.all { it.volumeInfo.title.isNotBlank() })
        }
    }

    @Test
    fun apiReturnsRightVolume() {
        /*
        The following books will be requested from the API and will be ensured that the titles match the ID's
        * */

        val books: Map<String, String> = mapOf(
            /*      ID               Title       */
            "qtcIkAEACAAJ" to "Kotlin in Action",
            "I-OsDwAAQBAJ" to "Kotlin Zero to Hero",
            "Xje2DwAAQBAJ" to "Mastering Kotlin",
        )

        val instance = getInstance()

        runBlocking {
            for (book in books) {
                val volume = instance.volume(book.key)

                assertEquals(book.value, volume.volumeInfo.title)

                // The delay is for not letting the server think it's being attacked :)
                delay(500)
            }
        }
    }
}