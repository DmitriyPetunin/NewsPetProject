package com.example.storage.di

import android.content.Context
import androidx.room.RoomDatabase
import dagger.Module
import androidx.room.Room.databaseBuilder
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.storage.dao.NewsDao
import com.example.storage.database.AppDataBase
import com.example.storage.database.AppDataBase.Companion.DATABASE_NAME


import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context):AppDataBase {
        return databaseBuilder(
            context,
            AppDataBase::class.java, DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()
    }


    @Provides
    @Singleton
    fun provideNewsDao(
        db: AppDataBase
    ): NewsDao = db.getNewsDao()
}