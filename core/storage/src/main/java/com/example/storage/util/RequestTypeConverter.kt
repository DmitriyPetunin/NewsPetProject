package com.example.storage.util

import androidx.room.TypeConverter
import com.example.storage.RequestType

class RequestTypeConverter {

    @TypeConverter
    fun fromRequestType(type: RequestType):String{
        return type.name
    }

    @TypeConverter
    fun toRequestType(name: String): RequestType {
        return RequestType.valueOf(name)
    }
}