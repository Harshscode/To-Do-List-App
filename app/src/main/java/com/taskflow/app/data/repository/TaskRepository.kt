package com.taskflow.app.data.repository

import com.taskflow.app.data.local.TaskDao
import com.taskflow.app.data.local.TaskEntity
import com.taskflow.app.domain.model.Task
import com.taskflow.app.domain.model.TaskCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskDao: TaskDao) {

    fun getAllActiveTasks(): Flow<List<Task>> {
        return taskDao.getAllActive().map { entities ->
            entities.map { it.toTask() }
        }
    }

    fun getAllCompletedTasks(): Flow<List<Task>> {
        return taskDao.getAllCompleted().map { entities ->
            entities.map { it.toTask() }
        }
    }

    fun searchTasks(query: String): Flow<List<Task>> {
        return taskDao.searchTasks(query).map { entities ->
            entities.map { it.toTask() }
        }
    }

    fun searchAllTasks(query: String): Flow<List<Task>> {
        return taskDao.searchAllTasks(query).map { entities ->
            entities.map { it.toTask() }
        }
    }

    suspend fun getTaskById(taskId: Long): Task? {
        return taskDao.getTaskById(taskId)?.toTask()
    }

    suspend fun insertTask(task: Task): Long {
        return taskDao.insert(task.toEntity())
    }

    suspend fun updateTask(task: Task) {
        taskDao.update(task.toEntity())
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(task.toEntity())
    }

    suspend fun deleteTaskById(taskId: Long) {
        taskDao.deleteById(taskId)
    }

    suspend fun toggleTaskCompletion(taskId: Long, isCompleted: Boolean) {
        taskDao.updateCompletionStatus(taskId, isCompleted)
    }

    private fun TaskEntity.toTask(): Task {
        val categoryEnum = try {
            TaskCategory.valueOf(category)
        } catch (e: Exception) {
            TaskCategory.PERSONAL
        }
        return Task(
            id = id,
            title = title,
            description = description,
            dueDate = dueDate,
            priority = priority,
            isCompleted = isCompleted,
            category = categoryEnum,
            reminderEnabled = reminderEnabled,
            reminderTime = reminderTime,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun Task.toEntity(): TaskEntity {
        return TaskEntity(
            id = id,
            title = title,
            description = description,
            dueDate = dueDate,
            priority = priority,
            isCompleted = isCompleted,
            category = category.name,
            reminderEnabled = reminderEnabled,
            reminderTime = reminderTime,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
