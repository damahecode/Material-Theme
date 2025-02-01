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
import com.code.damahe.material.app.ThemeType
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
        is Success -> when (themeUiState.theme.themeType) {
            ThemeType.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            ThemeType.LIGHT -> false
            ThemeType.DARK -> true
        }
    }

    /**
     * The default light scrim, as defined by androidx and the platform:
     * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
     */
    val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

    /**
     * The default dark scrim, as defined by androidx and the platform:
     * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
     */
    val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
}