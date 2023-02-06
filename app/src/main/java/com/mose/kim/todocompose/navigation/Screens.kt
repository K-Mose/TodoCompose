package com.mose.kim.todocompose.navigation

import androidx.navigation.NavHostController
import com.mose.kim.todocompose.util.Action
import com.mose.kim.todocompose.util.Constants

class Screens(navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate(route = "list/${Action.NO_ACTION.name}") {
            popUpTo(Constants.SPLASH_SCREEN) {inclusive = true}
        }
    }

    val list: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")
    }

    val task: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action.name}") {
            popUpTo(Constants.LIST_SCREEN) {inclusive = true}
        }
    }
}