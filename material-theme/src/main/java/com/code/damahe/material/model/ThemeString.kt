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

package com.code.damahe.material.model

import com.code.damahe.material.app.ThemeConfig

data class ThemeString(
    val title: String = ThemeConfig.TITLE,
    val loading: String = ThemeConfig.LOADING,
    val ok: String = ThemeConfig.OK,
    val brandDefault: String = ThemeConfig.BRAND_DEFAULT,
    val brandDynamic: String = ThemeConfig.BRAND_DYNAMIC,
    val useGradientColors: String = ThemeConfig.USE_GRADIENT_COLORS,
    val yes: String = ThemeConfig.YES,
    val no: String = ThemeConfig.NO,
    val darkModePreference: String = ThemeConfig.DARK_MODE_PREFERENCE,
    val systemDefault: String = ThemeConfig.SYSTEM_DEFAULT,
    val light: String = ThemeConfig.LIGHT,
    val dark: String = ThemeConfig.DARK,
)
