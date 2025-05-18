package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.model.Insight
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.remote.TodoApiService
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoDao: TodoDao,
    private val todoApiService: TodoApiService
) {
    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos()

    fun getCompletedTodos(): Flow<List<Todo>> = todoDao.getCompletedTodos()

    fun searchTodos(query: String): Flow<List<Todo>> = todoDao.searchTodos(query)

    suspend fun getTodoById(id: Long): Todo? = todoDao.getTodoById(id)

    suspend fun addTodo(todo: Todo): Long = todoDao.insertTodo(todo)

    suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo)

    suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)

    suspend fun markAsDone(todo: Todo) {
        val updatedTodo = todo.copy(isDone = true, doneTime = Date())
        todoDao.updateTodo(updatedTodo)
    }

    suspend fun getInsights(): Insight {
        val totalTasks = todoDao.getTotalTaskCount()
        val completedTasks = todoDao.getCompletedTaskCount()
        val pendingTasks = totalTasks - completedTasks
        val completionRate = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f
        val upcomingDeadlines = todoDao.getUpcomingDeadlines(Date())

        // This would be more sophisticated in a real app
        val mostProductiveDay = "Tuesday"

        return Insight(
            totalTasks = totalTasks,
            completedTasks = completedTasks,
            pendingTasks = pendingTasks,
            completionRate = completionRate,
            upcomingDeadlines = upcomingDeadlines,
            mostProductiveDay = mostProductiveDay
        )
    }

    // Helper function to format today's date
    fun getTodayFormattedDate(): String {
        val dateFormat = SimpleDateFormat("EEEE, MMM dd yyyy", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }
}