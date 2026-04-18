package com.taskflow.app

import android.app.Application
import com.taskflow.app.data.local.TaskDatabase
import com.taskflow.app.data.repository.TaskRepository

class TaskFlowApplication : Application() {

    val database: TaskDatabase by lazy { TaskDatabase.getDatabase(this) }
    val repository: TaskRepository by lazy { TaskRepository(database.taskDao()) }

    companion object {
        lateinit var instance: TaskFlowApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
