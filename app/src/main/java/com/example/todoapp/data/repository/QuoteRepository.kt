package com.example.todoapp.data.repository

import com.example.todoapp.data.local.QuoteDao
import com.example.todoapp.data.model.Quote
import com.example.todoapp.data.remote.TodoApiService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val quoteDao: QuoteDao,
    private val todoApiService: TodoApiService
) {
    suspend fun getDailyQuote(): Quote {
        val today = getTodayWithoutTime()

        // Check if we already have a quote for today
        val savedQuote = quoteDao.getQuoteForDate(today)
        if (savedQuote != null) {
            return savedQuote
        }

        // If not, fetch from API and save
        return try {
            val response = todoApiService.getDailyQuote()
            val quote = Quote(
                id = response.id,
                text = response.quote,
                author = response.author,
                date = today
            )
            quoteDao.insertQuote(quote)
            quote
        } catch (e: Exception) {
            // Fallback quote if API fails
            Quote(
                id = "fallback",
                text = "The best way to predict the future is to create it.",
                author = "Abraham Lincoln",
                date = today
            )
        }
    }

    private fun getTodayWithoutTime(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}