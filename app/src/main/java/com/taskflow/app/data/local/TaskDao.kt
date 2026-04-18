package com.taskflow.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity): Long

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteById(taskId: Long)

    @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY isCompleted ASC, priority ASC, dueDate ASC")
    fun getAllActive(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY updatedAt DESC")
    fun getAllCompleted(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): TaskEntity?

    @Query("""
        SELECT * FROM tasks
        WHERE (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        AND isCompleted = 0
        ORDER BY isCompleted ASC, priority ASC, dueDate ASC
    """)
    fun searchTasks(query: String): Flow<List<TaskEntity>>

    @Query("""
        SELECT * FROM tasks
        WHERE (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        ORDER BY isCompleted ASC, priority ASC, dueDate ASC
    """)
    fun searchAllTasks(query: String): Flow<List<TaskEntity>>

    @Query("UPDATE tasks SET isCompleted = :isCompleted, updatedAt = :updatedAt WHERE id = :taskId")
    suspend fun updateCompletionStatus(taskId: Long, isCompleted: Boolean, updatedAt: Long = System.currentTimeMillis())
}
