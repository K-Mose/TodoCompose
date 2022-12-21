package com.mose.kim.todocompose.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mose.kim.todocompose.data.TodoDatabase
import com.mose.kim.todocompose.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideDao(database: TodoDatabase) = database.todoDao()
}