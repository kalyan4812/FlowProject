package com.example.flowproject.source.local

import androidx.room.*
import com.example.flowproject.models.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Delete
    suspend fun deleteTask(task: Task): Int

    @Update
    suspend fun updateTask(task: Task): Int

    @Query("SELECT * FROM TASK_TABLE WHERE id=:id")
    suspend fun getTaskById(id: Int): Task?

    @Query("SELECT * FROM TASK_TABLE")
    fun getTasks(): Flow<List<Task>>
}