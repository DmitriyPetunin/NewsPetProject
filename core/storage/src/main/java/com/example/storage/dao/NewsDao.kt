package com.example.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storage.entities.ArticleEntity
import com.example.storage.entities.RequestEntity


@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: RequestEntity)

    @Query("Select * from ${RequestEntity.REQUEST_TABLE_NAME} where `query` = :input")
    suspend fun getReqByQuery(input:String):RequestEntity?


    @Query("DELETE FROM ${RequestEntity.REQUEST_TABLE_NAME} WHERE id = :id")
    suspend fun deleteOldRequests(id: String)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(news:List<ArticleEntity>)



    @Query("SELECT * FROM ${ArticleEntity.ARTICLE_TABLE_NAME} WHERE requestId = :requestId")
    suspend fun getArticlesByRequestId(requestId: String): List<ArticleEntity>


    @Query("Select * from ${ArticleEntity.ARTICLE_TABLE_NAME}")
    suspend fun getAll():List<ArticleEntity>

    @Query("Select * from ${ArticleEntity.ARTICLE_TABLE_NAME} where id = :uuid ")
    suspend fun getArticleById(uuid:String):ArticleEntity

}