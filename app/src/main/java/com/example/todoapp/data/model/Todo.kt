package com.example.todoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val date: Date,
    val deadline: Date,
    val isDone: Boolean = false,
    val doneTime: Date? = null,
    val shortNote: String = ""
)

// API response model
data class TodoResponse(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val deadline: String,
    val completed: Boolean,
    val completedAt: String?
)