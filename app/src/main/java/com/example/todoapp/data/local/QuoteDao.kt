package com.example.todoapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.data.model.Quote
import java.util.Date

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes WHERE date = :date LIMIT 1")
    suspend fun getQuoteForDate(date: Date): Quote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: Quote)
}