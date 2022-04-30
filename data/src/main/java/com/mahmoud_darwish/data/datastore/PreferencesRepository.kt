package com.mahmoud_darwish.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mahmoud_darwish.data.di.AppIOCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @AppIOCoroutineScope
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

