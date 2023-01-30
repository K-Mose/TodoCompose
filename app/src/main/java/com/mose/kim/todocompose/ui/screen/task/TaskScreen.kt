package com.mose.kim.todocompose.ui.screen.task

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import com.mose.kim.todocompose.components.PriorityDropDown
import com.mose.kim.todocompose.data.model.Priority
import com.mose.kim.todocompose.data.model.ToDoTask
import com.mose.kim.todocompose.ui.viewmodel.SharedViewModel
import com.mose.kim.todocompose.util.Action

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    // sharedViewModel에서 값 관찰 후 변경 적용
    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        },
        content = {
            Log.i("PADDING :: " , "$it")

            TaskContent(
                title = title,
                onTitleChange = { title ->
                    sharedViewModel.updateTitle(title)
                },
                description = description,
                onDescriptionChange = { description ->
                    sharedViewModel.description.value = description
                },
                priority = priority,
                onPrioritySelected = { selectedPriority ->
                    sharedViewModel.priority.value = selectedPriority
                }
            )
        }
    )
}