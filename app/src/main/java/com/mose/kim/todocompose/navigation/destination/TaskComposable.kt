package com.mose.kim.todocompose.navigation.destination

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mose.kim.todocompose.util.Action
import com.mose.kim.todocompose.util.Constants

fun NavGraphBuilder.taskComposable(
    navigateToTaskList: (Action) -> Unit
) {
    composable(
        route = Constants.TASK_SCREEN,
        arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(Constants.TASK_ARGUMENT_KEY)
        Log.d("TaskComposable :: ", taskId.toString())
    }
}