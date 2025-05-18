package com.example.todoapp.data.model

data class Insight(
    val totalTasks: Int,
    val completedTasks: Int,
    val pendingTasks: Int,
    val completionRate: Float,
    val upcomingDeadlines: List<Todo>,
    val mostProductiveDay: String
)
