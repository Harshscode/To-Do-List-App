package com.taskflow.app

import com.taskflow.app.domain.model.Priority
import com.taskflow.app.domain.model.Task
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

class TaskTest {

    @Test
    fun `Task with priority 1 should have HIGH priority level`() {
        val task = Task(title = "High Priority Task", priority = 1)
        assertEquals(Priority.HIGH, task.priorityLevel)
    }

    @Test
    fun `Task with priority 2 should have MEDIUM priority level`() {
        val task = Task(title = "Medium Priority Task", priority = 2)
        assertEquals(Priority.MEDIUM, task.priorityLevel)
    }

    @Test
    fun `Task with priority 3 should have LOW priority level`() {
        val task = Task(title = "Low Priority Task", priority = 3)
        assertEquals(Priority.LOW, task.priorityLevel)
    }

    @Test
    fun `Task with default priority 2 should have MEDIUM priority level`() {
        val task = Task(title = "Default Task")
        assertEquals(Priority.MEDIUM, task.priorityLevel)
    }

    @Test
    fun `isDueToday should return true when dueDate is today`() {
        val today = LocalDate.now()
        val dueDateMillis = today.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val task = Task(title = "Today's Task", dueDate = dueDateMillis)
        assertTrue(task.isDueToday())
    }

    @Test
    fun `isDueToday should return false when dueDate is not today`() {
        val tomorrow = LocalDate.now().plusDays(1)
        val dueDateMillis = tomorrow.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val task = Task(title = "Tomorrow's Task", dueDate = dueDateMillis)
        assertFalse(task.isDueToday())
    }

    @Test
    fun `isDueToday should return false when dueDate is null`() {
        val task = Task(title = "No Due Date Task", dueDate = null)
        assertFalse(task.isDueToday())
    }

    @Test
    fun `isDueUpcoming should return true when dueDate is in future`() {
        val futureDate = LocalDate.now().plusDays(5)
        val dueDateMillis = futureDate.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val task = Task(title = "Future Task", dueDate = dueDateMillis)
        assertTrue(task.isDueUpcoming())
    }

    @Test
    fun `isDueUpcoming should return false when dueDate is in past`() {
        val pastDate = LocalDate.now().minusDays(1)
        val dueDateMillis = pastDate.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val task = Task(title = "Past Task", dueDate = dueDateMillis)
        assertFalse(task.isDueUpcoming())
    }

    @Test
    fun `isDueUpcoming should return false when dueDate is null`() {
        val task = Task(title = "No Due Date Task", dueDate = null)
        assertFalse(task.isDueUpcoming())
    }

    @Test
    fun `isDuePast should return true when dueDate is in past`() {
        val pastDate = LocalDate.now().minusDays(1)
        val dueDateMillis = pastDate.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val task = Task(title = "Overdue Task", dueDate = dueDateMillis)
        assertTrue(task.isDuePast())
    }

    @Test
    fun `isDuePast should return false when dueDate is today`() {
        val today = LocalDate.now()
        val dueDateMillis = today.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        val task = Task(title = "Today's Task", dueDate = dueDateMillis)
        assertFalse(task.isDuePast())
    }

    @Test
    fun `Task default values should be correct`() {
        val task = Task(title = "Test Task")
        assertEquals(0L, task.id)
        assertEquals("Test Task", task.title)
        assertNull(task.description)
        assertNull(task.dueDate)
        assertEquals(2, task.priority)
        assertFalse(task.isCompleted)
        assertTrue(task.createdAt > 0)
        assertTrue(task.updatedAt > 0)
    }

    @Test
    fun `Task with all fields should preserve values`() {
        val createdAt = System.currentTimeMillis()
        val updatedAt = System.currentTimeMillis()
        val dueDate = LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault())
            .toInstant().toEpochMilli()

        val task = Task(
            id = 123,
            title = "Complete Task",
            description = "Full description",
            dueDate = dueDate,
            priority = 1,
            isCompleted = true,
            createdAt = createdAt,
            updatedAt = updatedAt
        )

        assertEquals(123L, task.id)
        assertEquals("Complete Task", task.title)
        assertEquals("Full description", task.description)
        assertEquals(dueDate, task.dueDate)
        assertEquals(1, task.priority)
        assertTrue(task.isCompleted)
        assertEquals(createdAt, task.createdAt)
        assertEquals(updatedAt, task.updatedAt)
    }
}
