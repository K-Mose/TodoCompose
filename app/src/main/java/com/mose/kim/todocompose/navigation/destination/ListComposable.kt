package com.mose.kim.todocompose.navigation.destination

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mose.kim.todocompose.ui.screen.list.ListScreen
import com.mose.kim.todocompose.ui.viewmodel.SharedViewModel
import com.mose.kim.todocompose.util.Constants.LIST_SCREEN
import com.mose.kim.todocompose.util.Constants.LIST_ARGUMENT_KEY
import com.mose.kim.todocompose.util.toAction

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        //
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        Log.i("listComposable1-ACTION :: ", "${navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY)} | ${action.name}")
        //
        LaunchedEffect(key1 = action) {
            Log.i("listComposable2-ACTION :: ", "${action.name}")
            sharedViewModel.action.value = action
        }


        ListScreen(
            navigationToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}