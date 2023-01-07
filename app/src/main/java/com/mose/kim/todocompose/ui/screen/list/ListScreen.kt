package com.mose.kim.todocompose.ui.screen.list

import android.annotation.SuppressLint
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mose.kim.todocompose.R
import com.mose.kim.todocompose.ui.theme.fabBackgroundColor
import com.mose.kim.todocompose.ui.viewmodel.SharedViewModel
import com.mose.kim.todocompose.util.SearchAppBarState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigationToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {

    //
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    Scaffold(
        topBar = {
            ListAppBar(
                // ViewModel 값 변경이 관찰되면 re-compose
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {},
        floatingActionButton = {
            ListFab(navigationToTaskScreen = navigationToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    navigationToTaskScreen: (Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            navigationToTaskScreen(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}