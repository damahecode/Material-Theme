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

package com.code.damahe.material.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.damahe.material.app.DarkThemeConfig
import com.code.damahe.material.app.ThemeBrand
import com.code.damahe.material.data.ThemeDataRepository
import com.code.damahe.material.model.UserEditableTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeDataRepository: ThemeDataRepository,
) : ViewModel() {

    val themeUiState: StateFlow<ThemeUiState> = themeDataRepository.userEditableTheme.map {
        ThemeUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = ThemeUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

    fun updateThemeBrand(themeBrand: ThemeBrand) {
        viewModelScope.launch(Dispatchers.IO) {
            themeDataRepository.setThemeBrand(themeBrand)
        }
    }

    fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch(Dispatchers.IO) {
            themeDataRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    fun updateGradientColorsPreference(useGradientColors: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            themeDataRepository.setGradientColorsPreference(useGradientColors)
        }
    }
}

sealed interface ThemeUiState {
    data object Loading : ThemeUiState
    data class Success(val theme: UserEditableTheme) : ThemeUiState
}