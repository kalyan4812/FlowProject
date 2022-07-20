package com.example.flowproject.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flowproject.models.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract val getTaskDao: TaskDao
}