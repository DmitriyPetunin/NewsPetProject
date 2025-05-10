package com.example.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.storage.RequestType
import com.example.storage.entities.RequestEntity.Companion.REQUEST_TABLE_NAME


@Entity(tableName = REQUEST_TABLE_NAME)
class RequestEntity(
    @PrimaryKey()
    val id: String,
    val query: String,
    val type: RequestType,
    val timestamp: Long
) {
    companion object {
        const val REQUEST_TABLE_NAME = "request"
    }
}