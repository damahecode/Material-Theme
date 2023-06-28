package com.code.damahe.theme.model

import com.code.damahe.theme.config.DarkThemeConfig
import com.code.damahe.theme.config.ThemeBrand

/**
 * Represents the settings which the user can edit within the app.
 */
data class UserEditableTheme(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
)
