package com.example.todoapp.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _date = MutableStateFlow(Date())
    val date: StateFlow<Date> = _date.asStateFlow()

    private val _deadline = MutableStateFlow(Date())
    val deadline: StateFlow<Date> = _deadline.asStateFlow()

    private val _shortNote = MutableStateFlow("")
    val shortNote: StateFlow<String> = _shortNote.asStateFlow()

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved.asStateFlow()

    fun updateTitle(title: String) {
        _title.value = title
    }

    fun updateDescription(description: String) {
        _description.value = description
    }

    fun updateDate(date: Date) {
        _date.value = date
    }

    fun updateDeadline(deadline: Date) {
        _deadline.value = deadline
    }

    fun updateShortNote(note: String) {
        _shortNote.value = note
    }

    fun saveTodo() {
        viewModelScope.launch {
            val todo = Todo(
                title = _title.value,
                description = _description.value,
                date = _date.value,
                deadline = _deadline.value,
                shortNote = _shortNote.value
            )
            todoRepository.addTodo(todo)
            _isSaved.value = true
        }
    }

    fun resetSavedState() {
        _isSaved.value = false
    }
}