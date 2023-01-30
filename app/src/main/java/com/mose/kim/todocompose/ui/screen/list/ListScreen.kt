package com.mose.kim.todocompose.ui.screen.list

import android.annotation.SuppressLint
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
    // LaunchedEfffect - 컴포즤션이 시작되면 조건(key..)에 맞게 실행되며 범위 내에서 안전하게 종료
    // key 값에 state가 들어가 상태에 따라 트리거로 작동시킴
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    val action by sharedViewModel.action

    // collectAsState를 통해서 allTasks의 값들을 최신상태로 유지
    val allTasks by sharedViewModel.allTasks.collectAsState() // collectAsState - StateFlow를 통해 값을 가져오고 State를 통해 최신의 데이터를 가져옴
    //
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    sharedViewModel.handleDatabaseAction(action = action)

    Scaffold(
        topBar = {
            ListAppBar(
                // ViewModel 값 변경이 관찰되면 re-compose
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
                  ListContent(
                      allTasks,
                      navigationToTaskScreen
                  )
        },
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
            // taskId = -1 : todoTask 생성 페이지
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