package com.mose.kim.todocompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mose.kim.todocompose.navigation.destination.listComposable
import com.mose.kim.todocompose.navigation.destination.taskComposable
import com.mose.kim.todocompose.ui.viewmodel.SharedViewModel
import com.mose.kim.todocompose.util.Constants

@Composable
fun SetUpNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = Constants.LIST_SCREEN
    ) {
        listComposable(
            navigateToTaskScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
        taskComposable(navigateToTaskList = screen.list)
    }
}