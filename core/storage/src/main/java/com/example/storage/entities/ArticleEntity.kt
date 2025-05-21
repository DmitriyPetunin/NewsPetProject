package com.example.storage.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.storage.entities.ArticleEntity.Companion.ARTICLE_TABLE_NAME


@Entity(
    tableName = ARTICLE_TABLE_NAME,
    indices = [Index(value = ["requestId"])],
    foreignKeys = [
        ForeignKey(
            entity = RequestEntity::class,
            parentColumns = ["id"],
            childColumns = ["requestId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class ArticleEntity (
    @PrimaryKey()
    val id: String,
    val title: String,
    val description: String,
    val image_url: String,
    val published_at: String,
    val source: String,
    val requestId:String
){
    companion object{
        const val ARTICLE_TABLE_NAME = "article"
    }
}