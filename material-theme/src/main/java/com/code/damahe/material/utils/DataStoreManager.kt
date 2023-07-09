package com.code.damahe.material.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.code.damahe.material.config.ThemeConfig.PrefTheme
import com.code.damahe.material.config.DarkThemeConfig
import com.code.damahe.material.config.ThemeBrand
import com.code.damahe.material.model.UserEditableTheme
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PrefTheme.PREFERENCE_DATA_STORE)

    companion object {
        val themeBrandKey = intPreferencesKey(PrefTheme.THEME_BRAND_KEY)
        val darkThemeKey = intPreferencesKey(PrefTheme.DARK_THEME_KEY)
        val useDynamicColorKey = booleanPreferencesKey(PrefTheme.USE_DYNAMIC_COLOR_KEY)
    }

    fun getThemeFromDataStore() = context.datastore.data.map {
        UserEditableTheme(
            themeBrand = when (it[themeBrandKey]) {
                PrefTheme.THEME_BRAND_ANDROID -> ThemeBrand.ANDROID
                else -> ThemeBrand.DEFAULT
            },
            darkThemeConfig = when (it[darkThemeKey]) {
                PrefTheme.DARK_THEME_LIGHT -> DarkThemeConfig.LIGHT
                PrefTheme.DARK_THEME_DARK -> DarkThemeConfig.DARK
                else -> DarkThemeConfig.FOLLOW_SYSTEM
            },
            useDynamicColor = it[useDynamicColorKey] ?: false
        )
    }

    suspend fun saveThemeBrandDataStore(themeBrand: ThemeBrand) {
        context.datastore.edit {
            it[themeBrandKey] = when (themeBrand) {
                ThemeBrand.ANDROID -> PrefTheme.THEME_BRAND_ANDROID
                else -> PrefTheme.THEME_BRAND_DEFAULT
            }
        }
    }

    suspend fun saveDarkThemeConfigDataStore(darkThemeConfig: DarkThemeConfig) {
        context.datastore.edit {
            it[darkThemeKey] = when (darkThemeConfig) {
                DarkThemeConfig.LIGHT -> PrefTheme.DARK_THEME_LIGHT
                DarkThemeConfig.DARK -> PrefTheme.DARK_THEME_DARK
                else -> PrefTheme.DARK_THEME_FOLLOW_SYSTEM
            }
        }
    }

    suspend fun saveDynamicColorPreferenceDataStore(useDynamicColor: Boolean) {
        context.datastore.edit {
            it[useDynamicColorKey] = useDynamicColor
        }
    }

}

