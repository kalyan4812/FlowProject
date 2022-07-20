package com.example.flowproject.di.modules

import android.content.Context
import androidx.room.Room
import com.example.flowproject.source.local.TaskDatabase
import com.example.flowproject.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RoomModule {


    @Singleton
    @Provides
    fun provideDbName(): String {
        return DATABASE_NAME
    }

    @Singleton
    @Provides
    fun provideDatabase(name: String, @ApplicationContext context: Context):TaskDatabase {
        return Room.databaseBuilder(context, TaskDatabase::class.java, name)
            .fallbackToDestructiveMigration().build()
    }


}