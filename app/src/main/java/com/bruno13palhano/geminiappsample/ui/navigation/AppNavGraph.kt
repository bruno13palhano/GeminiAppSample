package com.bruno13palhano.geminiappsample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bruno13palhano.geminiappsample.ui.feature.chat.ChatRoute
import kotlinx.serialization.Serializable

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: MainRoute = MainRoute
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<MainRoute>(startDestination = HomeRoute) {
            composable<HomeRoute> {
                ChatRoute()
            }
        }
    }
}

@Serializable
object MainRoute

@Serializable
object HomeRoute