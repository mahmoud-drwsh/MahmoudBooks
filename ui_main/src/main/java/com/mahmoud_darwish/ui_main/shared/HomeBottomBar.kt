package com.mahmoud_darwish.ui_main.shared

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mahmoud_darwish.ui_main.destinations.DirectionDestination
import com.mahmoud_darwish.ui_main.destinations.FavoritesModuleInstallationProgressDestination
import com.mahmoud_darwish.ui_main.destinations.HomeContentDestination
import com.ramcosta.composedestinations.navigation.navigateTo

enum class BottomBarDestination(
    val direction: DirectionDestination,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Home(
        HomeContentDestination,
        Icons.Default.Home,
        com.mahmoud_darwish.data.R.string.home_navigation_bar_item_name
    ),
    Favorites(
        HomeContentDestination,
        Icons.Default.Bookmark,
        com.mahmoud_darwish.data.R.string.favorites_navigation_bar_item_name
    ),
    Settings(
        FavoritesModuleInstallationProgressDestination,
        Icons.Default.Bookmark,
        com.mahmoud_darwish.data.R.string.settings_navigation_bar_item_name
    ),
}

@Composable
fun BottomBar(
    navController: NavController
) {
    /*val currentDestination by navController.currentBackStackEntryAsState()*/
    val name: String = "Settings"

    BottomNavigation {
        BottomBarDestination.values().forEach { destination ->
            LaunchedEffect(destination) {
                println(destination.name)
            }

            BottomNavigationItem(
                selected = name == destination.name,
                onClick = {
                    navController.navigateTo(destination.direction) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = stringResource(destination.label)
                    )
                },
                label = { Text(stringResource(destination.label)) },
            )
        }
    }
}