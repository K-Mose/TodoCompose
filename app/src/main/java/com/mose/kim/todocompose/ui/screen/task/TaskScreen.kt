package com.mose.kim.todocompose.ui.screen.task

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import com.mose.kim.todocompose.components.PriorityDropDown
import com.mose.kim.todocompose.data.model.Priority
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
            var tempPriority by remember {
                mutableStateOf(selectedTask?.priority ?: Priority.NONE)
            }
            TaskContent(
                title = selectedTask?.title ?: "",
                onTitleChange = {},
                description = selectedTask?.description ?: "",
                onDescriptionChange = {},
                priority = tempPriority,
                onPrioritySelected = {}
            )
        }
    )
}