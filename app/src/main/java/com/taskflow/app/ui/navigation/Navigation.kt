package com.taskflow.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Today
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object AddTask : Screen("add_task")
    data object EditTask : Screen("edit_task/{taskId}") {
        fun createRoute(taskId: Long) = "edit_task/$taskId"
    }
    data object Settings : Screen("settings")
}

data class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem(
        label = "Today",
        selectedIcon = Icons.Filled.Today,
        unselectedIcon = Icons.Outlined.Today,
        route = "today"
    ),
    BottomNavItem(
        label = "Upcoming",
        selectedIcon = Icons.Filled.Schedule,
        unselectedIcon = Icons.Outlined.Schedule,
        route = "upcoming"
    ),
    BottomNavItem(
        label = "Completed",
        selectedIcon = Icons.Filled.CheckCircle,
        unselectedIcon = Icons.Outlined.CheckCircle,
        route = "completed"
    ),
    BottomNavItem(
        label = "Add",
        selectedIcon = Icons.Filled.Add,
        unselectedIcon = Icons.Outlined.Add,
        route = Screen.AddTask.route
    ),
    BottomNavItem(
        label = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        route = Screen.Settings.route
    )
)
