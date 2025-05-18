package com.example.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Insight
import com.example.todoapp.data.model.Quote
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.QuoteRepository
import com.example.todoapp.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val todos = todoRepository.getAllTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val completedTodos = todoRepository.getCompletedTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _filteredTodos = MutableStateFlow<List<Todo>>(emptyList())
    val filteredTodos: StateFlow<List<Todo>> = _filteredTodos.asStateFlow()

    private val _insights = MutableStateFlow<Insight?>(null)
    val insights: StateFlow<Insight?> = _insights.asStateFlow()

    private val _dailyQuote = MutableStateFlow<Quote?>(null)
    val dailyQuote: StateFlow<Quote?> = _dailyQuote.asStateFlow()

    private val _todayDate = MutableStateFlow("")
    val todayDate: StateFlow<String> = _todayDate.asStateFlow()

    init {
        _todayDate.value = todoRepository.getTodayFormattedDate()
        loadDailyQuote()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _filteredTodos.value = emptyList()
        } else {
            viewModelScope.launch {
                todoRepository.searchTodos(query)
                    .stateIn(viewModelScope)
                    .collect { results ->
                        _filteredTodos.value = results
                    }
            }
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.deleteTodo(todo)
        }
    }

    fun loadInsights() {
        viewModelScope.launch {
            _insights.value = todoRepository.getInsights()
        }
    }

    private fun loadDailyQuote() {
        viewModelScope.launch {
            _dailyQuote.value = quoteRepository.getDailyQuote()
        }
    }
}
