package com.code.damahe.material.config

enum class DarkThemeConfig {
    FOLLOW_SYSTEM, LIGHT, DARK
}

enum class ThemeBrand {
    DEFAULT, ANDROID
}

object ThemeConfig {

    object PrefTheme {
        const val PREFERENCE_DATA_STORE = "Damahe_Code_Theme"

        const val THEME_BRAND_KEY = "Damahe_Code_Theme_themeBrandKey"
        const val DARK_THEME_KEY = "Damahe_Code_Theme_darkThemeKey"
        const val USE_DYNAMIC_COLOR_KEY = "Damahe_Code_Theme_useDynamicColorKey"

        const val THEME_BRAND_DEFAULT = 0
        const val THEME_BRAND_ANDROID = 1

        const val DARK_THEME_FOLLOW_SYSTEM = 0
        const val DARK_THEME_LIGHT = 1
        const val DARK_THEME_DARK = 2
    }
}