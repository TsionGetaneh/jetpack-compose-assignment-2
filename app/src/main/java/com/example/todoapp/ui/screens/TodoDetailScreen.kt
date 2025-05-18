package com.example.todoapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.R
import com.example.todoapp.ui.viewmodel.TodoDetailViewModel
import java.text.SimpleDateFormat

import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    onBackClick: () -> Unit,
    viewModel: TodoDetailViewModel = hiltViewModel()
) {
    val todo by viewModel.todo.collectAsState()
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Task illustration
            Image(
                painter = painterResource(id = R.drawable.task_illustration),
                contentDescription = "Task Illustration",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )

            // Task details card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFB2DFDB) // Light blue/teal color as shown in screenshot
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    todo?.let { task ->
                        Text(
                            text = "Title of the task",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Description of the task",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Date of the task",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
                        Text(
                            text = dateFormat.format(task.date),
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Deadline of the task",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
                        Text(
                            text = dateFormat.format(task.deadline),
                            style = MaterialTheme.typography.bodyLarge
                        )

                        if (task.isDone && task.doneTime != null) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Done time",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Black
                            )
                            Text(
                                text = dateFormat.format(task.doneTime),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        if (task.shortNote.isNotBlank()) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Short note",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Black
                            )
                            Text(
                                text = task.shortNote,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            // Mark as done button
            if (todo != null && !todo!!.isDone) {
                Button(
                    onClick = { viewModel.markAsDone() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF81C784) // Green color for the button
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Task is Done")
                }
            }
        }
    }
}