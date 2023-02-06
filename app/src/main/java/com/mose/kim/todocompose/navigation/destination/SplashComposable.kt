package com.mose.kim.todocompose.navigation.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.mose.kim.todocompose.ui.screen.splash.SplashScreen
import com.mose.kim.todocompose.util.Constants

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = Constants.SPLASH_SCREEN,
        exitTransition = {
            slideOutVertically(
                animationSpec = tween(2000)
            )
        }
    ) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }
}