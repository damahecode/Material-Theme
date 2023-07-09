package com.code.damahe.material.model

import com.code.damahe.material.config.DarkThemeConfig
import com.code.damahe.material.config.ThemeBrand

/**
 * Represents the settings which the user can edit within the app.
 */
data class UserEditableTheme(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
)
