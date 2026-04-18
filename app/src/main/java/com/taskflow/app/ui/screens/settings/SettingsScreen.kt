package com.taskflow.app.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    var dynamicColorEnabled by remember { mutableStateOf(true) }
    var showBadgeEnabled by remember { mutableStateOf(false) }

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
            SectionHeader(title = "APPEARANCE")

            SettingsItem(
                icon = Icons.Default.Palette,
                title = "Theme",
                subtitle = "System default",
                onClick = { }
            )

            SettingsItemWithSwitch(
                icon = Icons.Default.WaterDrop,
                title = "Dynamic color",
                subtitle = "Use wallpaper colors",
                checked = dynamicColorEnabled,
                onCheckedChange = { dynamicColorEnabled = it }
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            SectionHeader(title = "TASK LIST PREFERENCES")

            SettingsItem(
                icon = Icons.Default.Home,
                title = "Default tab",
                subtitle = "Today",
                onClick = { }
            )

            SettingsItem(
                icon = Icons.Default.Sort,
                title = "Sort order",
                subtitle = "Due date first",
                onClick = { }
            )

            SettingsItemWithSwitch(
                icon = Icons.Default.List,
                title = "Show completed tasks count badge",
                subtitle = "Display count on tab",
                checked = showBadgeEnabled,
                onCheckedChange = { showBadgeEnabled = it }
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            SectionHeader(title = "DATA")

            SettingsItem(
                icon = Icons.Default.Download,
                title = "Export tasks",
                subtitle = "Download as CSV file",
                onClick = { }
            )

            SettingsItem(
                icon = Icons.Default.Delete,
                title = "Clear completed tasks",
                subtitle = "Remove all finished tasks",
                onClick = { },
                isDestructive = true
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    val contentColor = if (isDestructive) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    androidx.compose.material3.ListItem(
        headlineContent = {
            Text(
                text = title,
                color = contentColor
            )
        },
        supportingContent = {
            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    )
}

@Composable
private fun SettingsItemWithSwitch(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    androidx.compose.material3.ListItem(
        headlineContent = { Text(text = title) },
        supportingContent = {
            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}
