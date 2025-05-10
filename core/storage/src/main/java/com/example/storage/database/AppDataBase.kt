package com.example.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.storage.dao.NewsDao
import com.example.storage.entities.ArticleEntity
import com.example.storage.entities.RequestEntity
import com.example.storage.util.RequestTypeConverter


@Database(
    entities = [ArticleEntity::class, RequestEntity::class],
    version = 1,
    exportSchema = true
)

@TypeConverters(RequestTypeConverter::class)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

    companion object{
        const val DATABASE_NAME = "news.db"
    }
}