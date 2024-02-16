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

package com.code.damahe.material.config

enum class DarkThemeConfig {
    FOLLOW_SYSTEM, LIGHT, DARK
}

enum class ThemeBrand {
    DEFAULT, DYNAMIC
}

object ThemeConfig {

    object PrefTheme {
        const val PREFERENCE_DATA_STORE = "Material_Theme"

        const val THEME_BRAND_KEY = "Material_Theme_themeBrandKey"
        const val DARK_THEME_KEY = "Material_Theme_darkThemeKey"
        const val USE_GRADIENT_COLORS_KEY = "Material_Theme_useGradientColorsKey"

        const val THEME_BRAND_DEFAULT = 0
        const val THEME_BRAND_DYNAMIC = 1

        const val DARK_THEME_FOLLOW_SYSTEM = 0
        const val DARK_THEME_LIGHT = 1
        const val DARK_THEME_DARK = 2
    }
}