package com.mose.kim.todocompose.data.model

import androidx.compose.ui.graphics.Color
import com.mose.kim.todocompose.ui.theme.HighPriorityColor
import com.mose.kim.todocompose.ui.theme.LowPriorityColor
import com.mose.kim.todocompose.ui.theme.MediumPriorityColor
import com.mose.kim.todocompose.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}