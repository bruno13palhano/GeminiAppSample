package com.bruno13palhano.geminiappsample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.bruno13palhano.geminiappsample.ui.feature.chat.ChatRoute
import com.bruno13palhano.geminiappsample.ui.feature.home.HomeRoute

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Screen.Main = Screen.Main
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<Screen.Main>(startDestination = Screen.Home) {
            composable<Screen.Home> {
                HomeRoute { model ->
                    navController.navigate(Screen.Chat(model))
                }
            }
            composable<Screen.Chat> {
                val model = it.toRoute<Screen.Chat>().name

                ChatRoute(model = model)
            }
        }
    }
}