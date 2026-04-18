package com.taskflow.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val dueDate: Long? = null,
    val priority: Int = 2, // 1=High, 2=Medium, 3=Low
    val isCompleted: Boolean = false,
    val category: String = "PERSONAL",
    val reminderEnabled: Boolean = false,
    val reminderTime: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
