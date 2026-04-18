package com.taskflow.app

import com.taskflow.app.data.local.TaskDao
import com.taskflow.app.data.local.TaskEntity
import com.taskflow.app.data.repository.TaskRepository
import com.taskflow.app.domain.model.Task
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

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
    fun insertTask_returns_id() {
        runBlocking {
            val task = Task(title = "Test Task")
            whenever(taskDao.insert(any())).thenReturn(1L)

            val result = repository.insertTask(task)

            assertEquals(1L, result)
            verify(taskDao).insert(any())
        }
    }

    @Test
    fun getTaskById_returns_task() {
        runBlocking {
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
    }

    @Test
    fun deleteTask_removes_task() {
        runBlocking {
            val task = Task(
                id = 1,
                title = "Task to Delete"
            )

            repository.deleteTask(task)

            verify(taskDao).delete(any())
        }
    }
}
