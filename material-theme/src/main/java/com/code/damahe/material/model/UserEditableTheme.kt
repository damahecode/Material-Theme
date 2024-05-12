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

import com.code.damahe.material.app.DarkThemeConfig
import com.code.damahe.material.app.ThemeBrand

/**
 * Represents the settings which the user can edit within the app.
 */
data class UserEditableTheme(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useGradientColors: Boolean,
)
