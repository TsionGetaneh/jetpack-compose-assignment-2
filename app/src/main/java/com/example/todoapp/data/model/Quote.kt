package com.example.todoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey
    val id: String,
    val text: String,
    val author: String,
    val date: Date
)

// API response model
data class QuoteResponse(
    val id: String,
    val quote: String,
    val author: String
)