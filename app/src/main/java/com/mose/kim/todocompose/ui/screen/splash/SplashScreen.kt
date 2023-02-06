package com.mose.kim.todocompose.ui.screen.splash

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mose.kim.todocompose.ui.theme.splashScreenBackground
import com.mose.kim.todocompose.R
import com.mose.kim.todocompose.ui.theme.LOGO_HEIGHT
import com.mose.kim.todocompose.ui.theme.TodoComposeTheme
import com.mose.kim.todocompose.util.Constants.SPLASH_SCREEN_DELAY
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToListScreen: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        delay(SPLASH_SCREEN_DELAY)
        navigateToListScreen()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.splashScreenBackground),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(size = LOGO_HEIGHT),
            painter = painterResource(id = GetLogo()),
            contentDescription = stringResource(R.string.to_do_log)
        )
    }
}

// 테마에 맞게 로고를 얻기 위해
@Composable
fun GetLogo(): Int =
    if (isSystemInDarkTheme()) R.drawable.ic_logo_dark
    else R.drawable.ic_logo_light