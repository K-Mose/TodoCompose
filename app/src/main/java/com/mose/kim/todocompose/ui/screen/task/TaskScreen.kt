package com.mose.kim.todocompose.ui.screen.task

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.mose.kim.todocompose.data.model.ToDoTask
import com.mose.kim.todocompose.util.Action

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit
) {
    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = {
            it
        }
    )
}