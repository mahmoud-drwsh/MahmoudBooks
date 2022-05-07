package com.mahmoud_darwish.data.local.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverters {
    @TypeConverter
    fun listToJsonString(authors: List<String>): String {
        return Gson().toJson(authors) ?: ""
    }

    @TypeConverter
    fun jsonStringToList(authors: String): List<String> {
        return Gson().fromJson(authors, object : TypeToken<List<String>>() {}.type) ?: listOf()
    }
}