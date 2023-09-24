package com.sdprojects.expense.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sdprojects.expense.models.Category
import java.util.*

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun categoryToJson(category: Category): String {
        return gson.toJson(category)
    }

    @TypeConverter
    fun jsonToCategory(json: String): Category {
        return gson.fromJson(json, Category::class.java)
    }
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}