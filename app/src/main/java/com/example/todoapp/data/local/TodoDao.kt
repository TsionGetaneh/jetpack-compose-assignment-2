package com.example.todoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY deadline ASC")
    fun getAllTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Long): Todo?

    @Query("SELECT * FROM todos WHERE isDone = 1 ORDER BY doneTime DESC")
    fun getCompletedTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todos WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchTodos(query: String): Flow<List<Todo>>

    @Query("SELECT COUNT(*) FROM todos")
    suspend fun getTotalTaskCount(): Int

    @Query("SELECT COUNT(*) FROM todos WHERE isDone = 1")
    suspend fun getCompletedTaskCount(): Int

    @Query("SELECT * FROM todos WHERE deadline > :now AND isDone = 0 ORDER BY deadline ASC LIMIT 5")
    suspend fun getUpcomingDeadlines(now: Date): List<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo): Long

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)
}