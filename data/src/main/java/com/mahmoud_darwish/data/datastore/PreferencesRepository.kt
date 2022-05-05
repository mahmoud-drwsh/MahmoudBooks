package com.mahmoud_darwish.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PreferencesRepository constructor(
    private val dataStore: DataStore<Preferences>,
    private val scope: CoroutineScope
) {
    fun updateSearchQuery(term: String) {
        scope.launch {
            dataStore.edit {
                it[stringPreferencesKey(DataStorePreferenceKeyNames.SEARCH_QUERY.name)] = term
            }
        }
    }

    fun getLastEnteredSearchQuery(): Flow<String> = dataStore.data.map {
        it[stringPreferencesKey(DataStorePreferenceKeyNames.SEARCH_QUERY.name)] ?: ""
    }
}

