package com.example.teacher888.screnssss.main

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserStore(private val context:Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("link")
        private val LINK = stringPreferencesKey("link")
        private val CHECKED_USER = booleanPreferencesKey("checked")
    }

    val getLink: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LINK] ?: ""
    }

    val getCheckedBoolean : Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[CHECKED_USER]  ?: false
    }

    suspend fun changeLink(link:String){
        context.dataStore.edit {
            it[LINK] = link
        }
    }

    suspend fun changePermission(checked:Boolean){
        context.dataStore.edit {
            it[CHECKED_USER] = checked
        }
    }

}