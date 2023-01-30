package com.mose.kim.todocompose.navigation

import androidx.navigation.NavHostController
import com.mose.kim.todocompose.util.Action
import com.mose.kim.todocompose.util.Constants

class Screens(navController: NavHostController) {
    val list: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action.name}") {
            popUpTo(Constants.LIST_SCREEN) {inclusive = true}
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")
    }
}