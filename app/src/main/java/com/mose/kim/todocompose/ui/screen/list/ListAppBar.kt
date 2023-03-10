package com.mose.kim.todocompose.ui.screen.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.mose.kim.todocompose.R
import com.mose.kim.todocompose.components.DisplayAlertDialog
import com.mose.kim.todocompose.components.PriorityItem
import com.mose.kim.todocompose.data.model.Priority
import com.mose.kim.todocompose.ui.theme.LARGE_PADDING
import com.mose.kim.todocompose.ui.theme.TOP_APP_BAR_HEIGHT
import com.mose.kim.todocompose.ui.theme.topAppBarBackgroundColor
import com.mose.kim.todocompose.ui.theme.topAppBarContentColor
import com.mose.kim.todocompose.ui.viewmodel.SharedViewModel
import com.mose.kim.todocompose.util.Action
import com.mose.kim.todocompose.util.SearchAppBarState
import com.mose.kim.todocompose.util.TrailingIconState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    // viewModel의 observing을 통해서 View 변경
    when(searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    // 클릭 시 SearchAppBarState 변경되며, ViewModel에서 관찰.
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = {
                    sharedViewModel.persistSortState(it)
                },
                onDeleteAllConfirmed = {
                    sharedViewModel.action.value = Action.DELETE_ALL
                }
            )
        }
        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = {newText ->
                    sharedViewModel.searchTextState.value = newText
                },
                onSearchClicked = {
                    // 상태 변경과 값 초기화
                    sharedViewModel.searchDatabase(it)
                },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                }
            )
        }
    }
}

@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirmed: () -> Unit,
) {
    TopAppBar (
        title = {
            Text(
                text = stringResource(R.string.list_screen_title),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        actions = {
            ListAppBarAction (
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllConfirmed = onDeleteAllConfirmed
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

// contains three actions
@Composable
fun ListAppBarAction(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {
    var openDialog by remember{ mutableStateOf(false)}

    DisplayAlertDialog(
        title = stringResource(R.string.delete_all_tasks),
        message = stringResource(R.string.delete_all_tasks_confirmation),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = {
            onDeleteAllConfirmed()
        }
    )

    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteAllConfirmed = { openDialog = true})
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = onSearchClicked) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(R.string.sort_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.LOW)
                }
            ) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.HIGH)
                }
            ) {
                PriorityItem(priority = Priority.HIGH)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.NONE)
                }
            ) {
                PriorityItem(priority = Priority.NONE)
            }
        }
    }
}

@Composable
fun DeleteAllAction(
    onDeleteAllConfirmed: () -> Unit
) {
    var expanded by remember { mutableStateOf(false)}

    IconButton(onClick = {expanded = true}) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = stringResource(R.string.delete_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onDeleteAllConfirmed()
                }
            ) {
                Text(
                    modifier = Modifier.padding(start = LARGE_PADDING),
                    text = stringResource(R.string.delete_all_action),
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }

}

@Composable
fun SearchAppBar (
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    // 아이콘 상태, 리컴포즈에서 상태가 기억되기 위해 remember사용
    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.READY_TO_DELETE)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = stringResource(R.string.search_placeholder),
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            // 왼쪽 아이콘
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.disabled),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_icon),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            // 오른쪽 아이콘
            trailingIcon = {
                IconButton(onClick = {
                    when(trailingIconState) {
                        TrailingIconState.READY_TO_DELETE -> {
                            onTextChange("")
                            trailingIconState = TrailingIconState.READY_TO_CLOSE
                        }
                        TrailingIconState.READY_TO_CLOSE -> {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                                trailingIconState = TrailingIconState.READY_TO_DELETE
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_icon),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            // keyboard의 enter버튼 모양 변경
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            // 위의 버튼을 눌렀을 때
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClicked(text) }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DefaultAppBarPreview() {
    DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteAllConfirmed = {}
    )
}

@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(
        text = "",
        onTextChange = {},
        onSearchClicked = {},
        onCloseClicked = {}
    )
}