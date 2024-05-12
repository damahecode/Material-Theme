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

package com.code.damahe.material.data

import android.content.Context
import com.code.damahe.material.app.DarkThemeConfig
import com.code.damahe.material.app.ThemeBrand
import com.code.damahe.material.utils.DataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeDataRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private val dataStoreManager = DataStoreManager(context)

    val userEditableTheme = dataStoreManager.getThemeFromDataStore()

    suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        dataStoreManager.saveThemeBrandDataStore(themeBrand)
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        dataStoreManager.saveDarkThemeConfigDataStore(darkThemeConfig)
    }

    suspend fun setGradientColorsPreference(useGradientColors: Boolean) {
        dataStoreManager.saveGradientColorsPreferenceDataStore(useGradientColors)
    }
}