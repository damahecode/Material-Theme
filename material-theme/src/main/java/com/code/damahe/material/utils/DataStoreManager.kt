/*
 * Copyright (c) 2024 damahecode.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.code.damahe.material.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.code.damahe.material.app.ThemeConfig.PrefTheme
import com.code.damahe.material.app.DarkThemeConfig
import com.code.damahe.material.app.ThemeBrand
import com.code.damahe.material.model.UserEditableTheme
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PrefTheme.PREFERENCE_DATA_STORE)

    companion object {
        val themeBrandKey = intPreferencesKey(PrefTheme.THEME_BRAND_KEY)
        val darkThemeKey = intPreferencesKey(PrefTheme.DARK_THEME_KEY)
        val useGradientColorsKey = booleanPreferencesKey(PrefTheme.USE_GRADIENT_COLORS_KEY)
    }

    fun getThemeFromDataStore() = context.datastore.data.map {
        UserEditableTheme(
            themeBrand = when (it[themeBrandKey]) {
                PrefTheme.THEME_BRAND_DYNAMIC -> ThemeBrand.DYNAMIC
                else -> ThemeBrand.DEFAULT
            },
            darkThemeConfig = when (it[darkThemeKey]) {
                PrefTheme.DARK_THEME_LIGHT -> DarkThemeConfig.LIGHT
                PrefTheme.DARK_THEME_DARK -> DarkThemeConfig.DARK
                else -> DarkThemeConfig.FOLLOW_SYSTEM
            },
            useGradientColors = it[useGradientColorsKey] ?: true
        )
    }

    suspend fun saveThemeBrandDataStore(themeBrand: ThemeBrand) {
        context.datastore.edit {
            it[themeBrandKey] = when (themeBrand) {
                ThemeBrand.DYNAMIC -> PrefTheme.THEME_BRAND_DYNAMIC
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

    suspend fun saveGradientColorsPreferenceDataStore(useGradientColors: Boolean) {
        context.datastore.edit {
            it[useGradientColorsKey] = useGradientColors
        }
    }

}

