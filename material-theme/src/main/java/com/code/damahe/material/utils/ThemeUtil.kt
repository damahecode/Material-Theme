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

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.code.damahe.material.app.DarkThemeConfig
import com.code.damahe.material.app.ThemeBrand
import com.code.damahe.material.viewmodel.ThemeUiState.Loading
import com.code.damahe.material.viewmodel.ThemeUiState.Success
import com.code.damahe.material.viewmodel.ThemeUiState

object ThemeUtil {

    /**
     * Returns `ThemeBrand` if the Other theme should be used, as a function of the [themeUiState].
     */
    @Composable
    fun shouldUseOtherThemeBrand(
        themeUiState: ThemeUiState,
    ): ThemeBrand = when (themeUiState) {
        Loading -> ThemeBrand.DEFAULT
        is Success -> themeUiState.theme.themeBrand
    }

    /**
     * Returns `true` if the Gradient colors is disabled, as a function of the [themeUiState].
     */
    @Composable
    fun shouldDisableGradientColors(
        themeUiState: ThemeUiState,
    ): Boolean = when (themeUiState) {
        Loading -> false
        is Success -> !themeUiState.theme.useGradientColors
    }

    /**
     * Returns `true` if dark theme should be used, as a function of the [themeUiState] and the
     * current system context.
     */
    @Composable
    fun shouldUseDarkTheme(
        themeUiState: ThemeUiState,
    ): Boolean = when (themeUiState) {
        Loading -> isSystemInDarkTheme()
        is Success -> when (themeUiState.theme.darkThemeConfig) {
            DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            DarkThemeConfig.LIGHT -> false
            DarkThemeConfig.DARK -> true
        }
    }

}