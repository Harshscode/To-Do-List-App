package com.taskflow.app

import com.taskflow.app.data.local.TaskDao
import com.taskflow.app.data.local.TaskEntity
import com.taskflow.app.data.repository.TaskRepository
import com.taskflow.app.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.ZoneId

class TaskRepositoryTest {

    @Mock
    private lateinit var taskDao: TaskDao

    private lateinit var repository: TaskRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = TaskRepository(taskDao)
    }

    @Test
    fun `insertTask should insert task and return id`() = runBlocking {
        val task = Task(
            title = "Test Task",
            description = "Test Description",
            priority = 1
        )
        whenever(taskDao.insert(any())).thenReturn(1L)

        val result = repository.insertTask(task)

        assertEquals(1L, result)
        verify(taskDao).insert(any())
    }

    @Test
    fun `updateTask should update task in dao`() = runBlocking {
        val task = Task(
            id = 1,
            title = "Updated Task",
            priority = 2
        )

        repository.updateTask(task)

        verify(taskDao).update(any())
    }

    @Test
    fun `deleteTask should delete task from dao`() = runBlocking {
        val task = Task(
            id = 1,
            title = "Task to Delete"
        )

        repository.deleteTask(task)

        verify(taskDao).delete(any())
    }

    @Test
    fun `getTaskById should return task from dao`() = runBlocking {
        val taskEntity = TaskEntity(
            id = 1,
            title = "Test Task",
            priority = 2
        )
        whenever(taskDao.getTaskById(1)).thenReturn(taskEntity)

        val result = repository.getTaskById(1)

        assertNotNull(result)
        assertEquals("Test Task", result?.title)
    }

    @Test
    fun `getTaskById should return null for non-existent task`() = runBlocking {
        whenever(taskDao.getTaskById(999)).thenReturn(null)

        val result = repository.getTaskById(999)

        assertNull(result)
    }

    @Test
    fun `getAllActiveTasks should return active tasks from dao`() = runBlocking {
        val taskEntities = listOf(
            TaskEntity(id = 1, title = "Task 1", isCompleted = false),
            TaskEntity(id = 2, title = "Task 2", isCompleted = false)
        )
        whenever(taskDao.getAllActive()).thenReturn(flowOf(taskEntities))

        val result = repository.getAllActiveTasks().first()

        assertEquals(2, result.size)
        assertEquals("Task 1", result[0].title)
        assertEquals("Task 2", result[1].title)
    }

    @Test
    fun `getAllCompletedTasks should return completed tasks from dao`() = runBlocking {
        val taskEntities = listOf(
            TaskEntity(id = 1, title = "Completed Task", isCompleted = true)
        )
        whenever(taskDao.getAllCompleted()).thenReturn(flowOf(taskEntities))

        val result = repository.getAllCompletedTasks().first()

        assertEquals(1, result.size)
        assertTrue(result[0].isCompleted)
    }

    @Test
    fun `searchTasks should return matching tasks`() = runBlocking {
        val taskEntities = listOf(
            TaskEntity(id = 1, title = "Buy groceries", description = "Get milk"),
            TaskEntity(id = 2, title = "Buy clothes", description = "Shirts")
        )
        whenever(taskDao.searchTasks("buy")).thenReturn(flowOf(taskEntities))

        val result = repository.searchTasks("buy").first()

        assertEquals(2, result.size)
        assertTrue(result.all { it.title.contains("buy", ignoreCase = true) })
    }

    @Test
    fun `toggleTaskCompletion should update completion status`() = runBlocking {
        repository.toggleTaskCompletion(1, true)

        verify(taskDao).updateCompletionStatus(1, true, any())
    }

    @Test
    fun `insertTask should preserve all task fields`() = runBlocking {
        val dueDate = LocalDate.of(2024, 4, 15)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val task = Task(
            title = "Full Task",
            description = "Complete description",
            dueDate = dueDate,
            priority = 1,
            isCompleted = false
        )
        whenever(taskDao.insert(any())).thenReturn(1L)

        repository.insertTask(task)

        verify(taskDao).insert(any())
    }
}
