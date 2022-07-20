package com.example.flowproject.repositories

import com.example.flowproject.models.Task
import com.example.flowproject.source.local.TaskDao
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) : TaskRepoInterface {


    override suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task): Int {
        return taskDao.deleteTask(task)
    }

    override suspend fun updateTask(task: Task): Int {
        return taskDao.updateTask(task)
    }

    override suspend fun getTaskById(id: Int): Task? {
        return taskDao.getTaskById(id)
    }

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks()
    }
}