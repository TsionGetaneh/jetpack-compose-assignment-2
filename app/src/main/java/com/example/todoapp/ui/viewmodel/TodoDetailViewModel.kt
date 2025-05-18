package com.example.todoapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val todoId: Long = checkNotNull(savedStateHandle["todoId"])

    private val _todo = MutableStateFlow<Todo?>(null)
    val todo: StateFlow<Todo?> = _todo.asStateFlow()

    init {
        loadTodo()
    }

    private fun loadTodo() {
        viewModelScope.launch {
            _todo.value = todoRepository.getTodoById(todoId)
        }
    }

    fun markAsDone() {
        viewModelScope.launch {
            _todo.value?.let { todo ->
                todoRepository.markAsDone(todo)
                _todo.value = todo.copy(isDone = true, doneTime = java.util.Date())
            }
        }
    }
}
