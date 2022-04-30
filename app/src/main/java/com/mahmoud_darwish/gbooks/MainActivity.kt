package com.mahmoud_darwish.gbooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.mahmoud_darwish.presentation.home.Home
import dagger.hilt.android.AndroidEntryPoint

/* TODO: Fix the split module installation */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Home()
        }


        val splitInstallManager = SplitInstallManagerFactory.create(this)

        var favoritesSessionId = 0

        splitInstallManager.registerListener { it: SplitInstallSessionState ->
            if (it.sessionId() == favoritesSessionId) {
                println("Started downloading")
                try {
                    val percentageDownloaded: Long =
                        it.totalBytesToDownload() / it.bytesDownloaded()
                    println(percentageDownloaded)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        val request = SplitInstallRequest
            .newBuilder()
            .addModule(getString(R.string.title_favorites))
            .build()

        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener { sessionId ->
                favoritesSessionId = sessionId
            }
            .addOnFailureListener { exception -> println(exception.message) }
    }
}


