package com.example.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.ui.screens.AddTodoScreen
import com.example.todoapp.ui.screens.AchievementsScreen
import com.example.todoapp.ui.screens.InsightsScreen
import com.example.todoapp.ui.screens.QuotesScreen
import com.example.todoapp.ui.screens.TodoDetailScreen
import com.example.todoapp.ui.screens.TodoListScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "todoList"
    ) {
        composable("todoList") {
            TodoListScreen(
                onTodoClick = { todoId ->
                    navController.navigate("todoDetail/$todoId")
                },
                onAddClick = {
                    navController.navigate("addTodo")
                },
                onInsightsClick = {
                    navController.navigate("insights")
                },
                onQuotesClick = {
                    navController.navigate("quotes")
                },
                onAchievementsClick = {
                    navController.navigate("achievements")
                }
            )
        }

        composable(
            route = "todoDetail/{todoId}",
            arguments = listOf(
                navArgument("todoId") { type = NavType.LongType }
            )
        ) {
            TodoDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("addTodo") {
            AddTodoScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable("insights") {
            InsightsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("quotes") {
            QuotesScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("achievements") {
            AchievementsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}