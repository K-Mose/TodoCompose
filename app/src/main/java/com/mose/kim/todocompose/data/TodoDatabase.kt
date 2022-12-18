package com.mose.kim.todocompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mose.kim.todocompose.data.model.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): ToDoDao
}