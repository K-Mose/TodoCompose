package com.mose.kim.todocompose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mose.kim.todocompose.ui.screen.splash.SplashScreen
import com.mose.kim.todocompose.util.Constants

fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = Constants.SPLASH_SCREEN
    ) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }
}