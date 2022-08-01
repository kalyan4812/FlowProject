package com.example.flowproject.di.modules

import com.example.flowproject.repositories.TaskRepository
import com.example.flowproject.source.local.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskDao)
    }
}