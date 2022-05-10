package com.mahmoud_darwish.data.local.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * This is needed to make Room able to save lists of strings
 * */
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