package com.taskflow.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.taskflow.app.data.repository.TaskRepository
import com.taskflow.app.domain.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeUiState(
    val todayTasks: List<Task> = emptyList(),
    val upcomingTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList(),
    val searchQuery: String = "",
    val searchResults: List<Task> = emptyList(),
    val isSearching: Boolean = false,
    val selectedTab: Int = 0,
    val isLoading: Boolean = true,
    val deletedTask: Task? = null,
    val showUndoSnackbar: Boolean = false
)

class HomeViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedTab = MutableStateFlow(0)
    private val _deletedTask = MutableStateFlow<Task?>(null)
    private val _showUndoSnackbar = MutableStateFlow(false)

    // Group the UI state flows together
    private val uiControlFlows = combine(
        _searchQuery,
        _selectedTab,
        _deletedTask,
        _showUndoSnackbar
    ) { searchQuery, selectedTab, deletedTask, showUndo ->
        UiControlState(searchQuery, selectedTab, deletedTask, showUndo)
    }

    // Combine with repository flows (task data)
    val uiState: StateFlow<HomeUiState> = combine(
        repository.getAllActiveTasks(),
        repository.getAllCompletedTasks(),
        uiControlFlows
    ) { activeTasks, completedTasks, controlState ->
        val todayTasks = activeTasks.filter { task -> task.isDueToday() }
        val upcomingTasks = activeTasks.filter { task -> task.isDueUpcoming() || task.dueDate == null }

        val searchResults = if (controlState.searchQuery.isNotBlank()) {
            activeTasks.filter { task ->
                task.title.contains(controlState.searchQuery, ignoreCase = true) ||
                task.description?.contains(controlState.searchQuery, ignoreCase = true) == true
            }
        } else {
            emptyList()
        }

        HomeUiState(
            todayTasks = todayTasks.sortedBy { it.priority },
            upcomingTasks = upcomingTasks.sortedBy { it.priority },
            completedTasks = completedTasks.sortedByDescending { it.updatedAt },
            searchQuery = controlState.searchQuery,
            searchResults = searchResults,
            isSearching = controlState.searchQuery.isNotBlank(),
            selectedTab = controlState.selectedTab,
            isLoading = false,
            deletedTask = controlState.deletedTask,
            showUndoSnackbar = controlState.showUndo
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeUiState()
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onTabSelected(tabIndex: Int) {
        _selectedTab.value = tabIndex
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            repository.toggleTaskCompletion(task.id, !task.isCompleted)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            _deletedTask.value = task
            _showUndoSnackbar.value = true
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            _deletedTask.value?.let { task ->
                repository.insertTask(task)
                _deletedTask.value = null
                _showUndoSnackbar.value = false
            }
        }
    }

    fun onSnackbarDismissed() {
        _showUndoSnackbar.value = false
        _deletedTask.value = null
    }

    class Factory(private val repository: TaskRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }
}

private data class UiControlState(
    val searchQuery: String,
    val selectedTab: Int,
    val deletedTask: Task?,
    val showUndo: Boolean
)
