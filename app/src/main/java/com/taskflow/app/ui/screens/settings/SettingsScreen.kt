package com.taskflow.app.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ListItem(
                headlineContent = { Text("About") },
                supportingContent = { Text("TaskFlow v1.0") },
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider()

            ListItem(
                headlineContent = { Text("Privacy") },
                supportingContent = { Text("All your data is stored locally on your device") },
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider()

            ListItem(
                headlineContent = { Text("Description") },
                supportingContent = {
                    Text(
                        "TaskFlow is an offline-first task management app designed to help you capture tasks quickly, plan them lightly, and track their status explicitly."
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
