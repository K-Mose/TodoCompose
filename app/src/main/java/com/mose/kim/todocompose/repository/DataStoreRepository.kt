package com.mose.kim.todocompose.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mose.kim.todocompose.data.model.Priority
import com.mose.kim.todocompose.util.Constants.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

// datasource를 Context의 확장 프로퍼티로 정의
// https://developer.android.com/topic/libraries/architecture/datastore?hl=ko#preferences-create
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferenceKeys {
        val sortState = stringPreferencesKey(name = PREFERENCE_NAME)
    }

    private val dataStore = context.dataStore

    // 설정된 priority로 dataStore에 저장 - https://developer.android.com/topic/libraries/architecture/datastore?hl=ko#preferences-write
    suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.sortState] = priority.name
        }
    }

    // 데이터 가져오기 - https://developer.android.com/topic/libraries/architecture/datastore?hl=ko#preferences-read
    val readSortState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val sortState = preferences[PreferenceKeys.sortState] ?: Priority.NONE.name
            sortState
        }

}