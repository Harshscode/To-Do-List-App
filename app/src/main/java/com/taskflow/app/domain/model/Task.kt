package com.taskflow.app.domain.model

import java.time.LocalDate
import java.time.ZoneId

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val dueDate: Long? = null,
    val priority: Int = 2,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    val priorityLevel: Priority
        get() = when (priority) {
            1 -> Priority.HIGH
            2 -> Priority.MEDIUM
            else -> Priority.LOW
        }

    fun isDueToday(): Boolean {
        if (dueDate == null) return false
        val today = LocalDate.now()
        val dueLocalDate = java.time.Instant.ofEpochMilli(dueDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        return today == dueLocalDate
    }

    fun isDueUpcoming(): Boolean {
        if (dueDate == null) return false
        val today = LocalDate.now()
        val dueLocalDate = java.time.Instant.ofEpochMilli(dueDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        return dueLocalDate.isAfter(today)
    }

    fun isDuePast(): Boolean {
        if (dueDate == null) return false
        val today = LocalDate.now()
        val dueLocalDate = java.time.Instant.ofEpochMilli(dueDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        return dueLocalDate.isBefore(today)
    }
}

enum class Priority {
    HIGH, MEDIUM, LOW
}
