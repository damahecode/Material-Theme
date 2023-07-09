package com.code.damahe.material.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.code.damahe.material.config.DarkThemeConfig
import com.code.damahe.material.config.ThemeBrand
import com.code.damahe.material.viewmodel.ThemeUiState.Loading
import com.code.damahe.material.viewmodel.ThemeUiState.Success
import com.code.damahe.material.viewmodel.ThemeUiState

object ThemeUtil {

    /**
     * Returns `true` if the Android theme should be used, as a function of the [themeUiState].
     */
    @Composable
    fun shouldUseAndroidTheme(
        themeUiState: ThemeUiState,
    ): Boolean = when (themeUiState) {
        Loading -> false
        is Success -> when (themeUiState.theme.themeBrand) {
            ThemeBrand.DEFAULT -> false
            ThemeBrand.ANDROID -> true
        }
    }

    /**
     * Returns `true` if the dynamic color is disabled, as a function of the [themeUiState].
     */
    @Composable
    fun shouldDisableDynamicTheming(
        themeUiState: ThemeUiState,
    ): Boolean = when (themeUiState) {
        Loading -> false
        is Success -> !themeUiState.theme.useDynamicColor
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