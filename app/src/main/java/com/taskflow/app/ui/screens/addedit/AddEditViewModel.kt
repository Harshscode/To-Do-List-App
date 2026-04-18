package com.taskflow.app.ui.screens.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.taskflow.app.data.repository.TaskRepository
import com.taskflow.app.domain.model.Priority
import com.taskflow.app.domain.model.Task
import com.taskflow.app.domain.model.TaskCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

data class AddEditUiState(
    val taskId: Long? = null,
    val title: String = "",
    val description: String = "",
    val dueDate: LocalDate? = null,
    val priority: Priority = Priority.MEDIUM,
    val category: TaskCategory = TaskCategory.PERSONAL,
    val reminderEnabled: Boolean = false,
    val reminderTime: Long? = null,
    val isCompleted: Boolean = false,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = false,
    val titleError: String? = null
)

class AddEditViewModel(
    private val repository: TaskRepository,
    private val taskId: Long? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState: StateFlow<AddEditUiState> = _uiState.asStateFlow()

    init {
        if (taskId != null && taskId != -1L) {
            loadTask(taskId)
        }
    }

    private fun loadTask(taskId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val task = repository.getTaskById(taskId)
            if (task != null) {
                _uiState.update {
                    it.copy(
                        taskId = task.id,
                        title = task.title,
                        description = task.description ?: "",
                        dueDate = task.dueDate?.let { date ->
                            java.time.Instant.ofEpochMilli(date)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        },
                        priority = task.priorityLevel,
                        category = task.category,
                        reminderEnabled = task.reminderEnabled,
                        reminderTime = task.reminderTime,
                        isCompleted = task.isCompleted,
                        isEditing = true,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onTitleChange(title: String) {
        _uiState.update { it.copy(title = title, titleError = null) }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onDueDateChange(date: LocalDate?) {
        _uiState.update { it.copy(dueDate = date) }
    }

    fun onPriorityChange(priority: Priority) {
        _uiState.update { it.copy(priority = priority) }
    }

    fun onCategoryChange(category: TaskCategory) {
        _uiState.update { it.copy(category = category) }
    }

    fun onReminderToggle(enabled: Boolean) {
        _uiState.update { it.copy(reminderEnabled = enabled) }
    }

    fun onCompletionToggle(completed: Boolean) {
        _uiState.update { it.copy(isCompleted = completed) }
    }

    fun saveTask() {
        val currentState = _uiState.value

        if (currentState.title.isBlank()) {
            _uiState.update { it.copy(titleError = "Title is required") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val dueDateMillis = currentState.dueDate?.atStartOfDay(ZoneId.systemDefault())
                ?.toInstant()?.toEpochMilli()

            val priorityInt = when (currentState.priority) {
                Priority.HIGH -> 1
                Priority.MEDIUM -> 2
                Priority.LOW -> 3
            }

            val existingTask = if (currentState.isEditing) {
                repository.getTaskById(currentState.taskId!!)
            } else null

            val task = Task(
                id = currentState.taskId ?: 0,
                title = currentState.title.trim(),
                description = currentState.description.trim().takeIf { it.isNotBlank() },
                dueDate = dueDateMillis,
                priority = priorityInt,
                isCompleted = currentState.isCompleted,
                category = currentState.category,
                reminderEnabled = currentState.reminderEnabled,
                reminderTime = currentState.reminderTime,
                createdAt = existingTask?.createdAt ?: System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            if (currentState.isEditing) {
                repository.updateTask(task)
            } else {
                repository.insertTask(task)
            }

            _uiState.update { it.copy(isLoading = false, isSaved = true) }
        }
    }

    fun deleteTask() {
        val currentState = _uiState.value
        if (currentState.taskId != null) {
            viewModelScope.launch {
                repository.deleteTaskById(currentState.taskId)
                _uiState.update { it.copy(isDeleted = true) }
            }
        }
    }

    class Factory(
        private val repository: TaskRepository,
        private val taskId: Long? = null
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddEditViewModel(repository, taskId) as T
        }
    }
}
