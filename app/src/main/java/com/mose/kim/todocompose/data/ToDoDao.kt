package com.mose.kim.todocompose.data

import androidx.room.*
import com.mose.kim.todocompose.data.model.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    // Flow를 사용하여 suspend fun을 쓰지 않음, Flow내에서 비동기식으로 데이터 처리
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM TODO_TABLE WHERE id = :taskId")
    fun getSelectedTask(taskId: Int): Flow<ToDoTask>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(toDoTask: ToDoTask)

    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    @Delete
    suspend fun deleteTask(id: Int)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>

    @Query("""
        SELECT * FROM todo_table ORDER BY 
            CASE 
                WHEN priority LIKE 'L%' THEN 1
                WHEN priority LIKE 'M%' THEN 2
                WHEN priority LIKE 'H%' THEN 3
            END
        """)
    fun sortByLowPriority(): Flow<List<ToDoTask>>

    @Query("""
        SELECT * FROM todo_table ORDER BY 
            CASE 
                WHEN priority LIKE 'L%' THEN 3
                WHEN priority LIKE 'M%' THEN 2
                WHEN priority LIKE 'H%' THEN 1
            END
        """)
    fun sortByHighPriority(): Flow<List<ToDoTask>>
}