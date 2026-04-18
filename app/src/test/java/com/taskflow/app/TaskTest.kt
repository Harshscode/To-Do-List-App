package com.taskflow.app

import com.taskflow.app.domain.model.Priority
import com.taskflow.app.domain.model.Task
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

class TaskTest {

    @Test
    fun `Task with priority HIGH returns HIGH priority level`() {
        val task = Task(title = "High Priority Task", priority = 1)
        assertEquals(Priority.HIGH, task.priorityLevel)
    }

    @Test
    fun `Task with priority MEDIUM returns MEDIUM priority level`() {
        val task = Task(title = "Medium Priority Task", priority = 2)
        assertEquals(Priority.MEDIUM, task.priorityLevel)
    }

    @Test
    fun `Task with priority LOW returns LOW priority level`() {
        val task = Task(title = "Low Priority Task", priority = 3)
        assertEquals(Priority.LOW, task.priorityLevel)
    }

    @Test
    fun `isDueToday returns true for today's date`() {
        val today = LocalDate.now()
        val dueDateMillis = today.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val task = Task(title = "Today's Task", dueDate = dueDateMillis)
        assertTrue(task.isDueToday())
    }

    @Test
    fun `isDueToday returns false for future date`() {
        val tomorrow = LocalDate.now().plusDays(1)
        val dueDateMillis = tomorrow.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val task = Task(title = "Tomorrow's Task", dueDate = dueDateMillis)
        assertFalse(task.isDueToday())
    }

    @Test
    fun `isDueUpcoming returns true for future date`() {
        val futureDate = LocalDate.now().plusDays(5)
        val dueDateMillis = futureDate.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val task = Task(title = "Future Task", dueDate = dueDateMillis)
        assertTrue(task.isDueUpcoming())
    }

    @Test
    fun `isDueUpcoming returns false for past date`() {
        val pastDate = LocalDate.now().minusDays(1)
        val dueDateMillis = pastDate.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val task = Task(title = "Past Task", dueDate = dueDateMillis)
        assertFalse(task.isDueUpcoming())
    }
}
