package com.taskflow.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.taskflow.app.ui.navigation.Screen
import com.taskflow.app.ui.screens.addedit.AddEditTaskScreen
import com.taskflow.app.ui.screens.addedit.AddEditViewModel
import com.taskflow.app.ui.screens.home.HomeScreen
import com.taskflow.app.ui.screens.home.HomeViewModel
import com.taskflow.app.ui.screens.settings.SettingsScreen
import com.taskflow.app.ui.theme.TaskFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskFlowTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TaskFlowApp()
                }
            }
        }
    }
}

@Composable
fun TaskFlowApp() {
    val navController = rememberNavController()
    val repository = TaskFlowApplication.instance.repository

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.Factory(repository)
            )
            HomeScreen(
                viewModel = homeViewModel,
                onAddTaskClick = {
                    navController.navigate(Screen.AddTask.route)
                },
                onTaskClick = { taskId ->
                    navController.navigate(Screen.EditTask.createRoute(taskId))
                }
            )
        }

        composable(Screen.AddTask.route) {
            val addEditViewModel: AddEditViewModel = viewModel(
                factory = AddEditViewModel.Factory(repository, null)
            )
            AddEditTaskScreen(
                viewModel = addEditViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditTask.route,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId") ?: -1L
            val addEditViewModel: AddEditViewModel = viewModel(
                factory = AddEditViewModel.Factory(repository, taskId)
            )
            AddEditTaskScreen(
                viewModel = addEditViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
