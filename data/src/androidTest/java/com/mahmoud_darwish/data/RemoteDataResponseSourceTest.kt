package com.mahmoud_darwish.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoud_darwish.data.remote.GoogleBooksApi
import com.mahmoud_darwish.data.remote.getBooksServiceInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RemoteDataResponseSourceTest {
    private lateinit var googleBooksApi: GoogleBooksApi

    private val searchTerm = "kotlin"

    @Before
    fun prepareRetrofitInstance() {
        googleBooksApi = getBooksServiceInstance()
    }


    @Test
    fun apiReturnsANonEmptyListOfResults() = runBlocking {
        val search = googleBooksApi.search(searchTerm)

        // assert the volumeDtos list is not empty
        assertTrue(search.items.isNotEmpty())

        // assert all books have valid ID's
        assertTrue(search.items.all { it.id.isNotBlank() })
    }

    @Test
    fun apiReturnsRightVolume() = runBlocking {
        /*
        The following books will be requested from the API and will be ensured that the titles match the ID'AppIoCoroutineScope
        * */
        @Suppress("SpellCheckingInspection")
        val books: Map<String, String> = mapOf(
            /*      ID               Title       */
            "qtcIkAEACAAJ" to "Kotlin in Action",
            "I-OsDwAAQBAJ" to "Kotlin Zero to Hero",
            "Xje2DwAAQBAJ" to "Mastering Kotlin",
        )

        for (book in books) {
            val volume = googleBooksApi.volume(book.key)

            assertEquals(book.value, volume.volumeInfo.title)

            // The delay is for not letting the server think it'AppIoCoroutineScope being attacked :)
            val subsequentRequestDelayTime: Long = 500
            delay(subsequentRequestDelayTime)
        }
    }
}