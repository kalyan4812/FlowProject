package com.example.flowproject.repositories

import androidx.room.*
import com.example.flowproject.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepoInterface {


    suspend fun insertTask(task: Task): Long


    suspend fun deleteTask(task: Task): Int


    suspend fun updateTask(task: Task): Int


    suspend fun getTaskById(id: Int): Task?


    fun getTasks(): Flow<List<Task>>
}