package com.example.flowproject.di.modules

import com.example.flowproject.repositories.TaskRepository
import com.example.flowproject.source.local.TaskDao
import com.example.flowproject.source.local.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // provide db instance.
    @Singleton
    @Provides
    fun getDbDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.getTaskDao
    }

    @Singleton
    @Provides
    fun provideRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskDao)
    }


}