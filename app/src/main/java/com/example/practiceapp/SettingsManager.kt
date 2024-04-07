package com.example.practiceapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.prefs.Preferences

private val Context.datastore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")
class SettingsManager(private val context: Context) {

    companion object{
        private val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
        private val SELECTED_LANGUAGE_CODE = stringPreferencesKey("selected_language_code")
    }

    suspend fun saveSelectedLanguage(appLanguage: AppLanguage) {
        context.applicationContext.datastore.edit { preferences ->
            preferences[SELECTED_LANGUAGE] = appLanguage.selectedLang
            preferences[SELECTED_LANGUAGE_CODE] = appLanguage.selectedLangCode
        }
    }

    private val languageFlow: Flow<AppLanguage> = context.applicationContext.datastore.data.map{ preferences ->
        AppLanguage(
            selectedLang = preferences[SELECTED_LANGUAGE] ?: "English",
            selectedLangCode = preferences[SELECTED_LANGUAGE_CODE] ?: "en"
        )
    }

    val currentLanguage: AppLanguage
        get() = runBlocking { languageFlow.first() }

    fun observeLanguageChanges(): Flow<AppLanguage> = languageFlow
}

data class AppLanguage(val selectedLang: String, val selectedLangCode: String)
