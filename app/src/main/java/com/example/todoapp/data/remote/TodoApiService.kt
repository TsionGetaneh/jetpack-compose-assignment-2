package com.example.todoapp.data.remote

import com.example.todoapp.data.model.QuoteResponse
import com.example.todoapp.data.model.TodoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): List<TodoResponse>

    @GET("todos/{id}")
    suspend fun getTodoById(@Path("id") id: String): TodoResponse

    @GET("quotes/daily")
    suspend fun getDailyQuote(): QuoteResponse
}