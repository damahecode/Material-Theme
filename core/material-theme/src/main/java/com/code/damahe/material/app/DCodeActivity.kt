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

package com.code.damahe.material.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.code.damahe.material.theme.DCodeAppTheme
import com.code.damahe.material.utils.ThemeUtil
import com.code.damahe.material.utils.ThemeUtil.darkScrim
import com.code.damahe.material.utils.ThemeUtil.lightScrim
import com.code.damahe.material.viewmodel.ThemeUiState
import com.code.damahe.material.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class DCodeActivity : ComponentActivity() {

    private val viewModel: ThemeViewModel by viewModels()
    protected var themeUiState: ThemeUiState by mutableStateOf(ThemeUiState.Loading)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.themeUiState
                    .onEach { themeUiState = it }
                    .collect()
            }
        }
    }

    /**
     * Your Main Composable Function here
     * ```
     * setContent {
     *     MainContent(themeUiState = themeUiState) {
     *         MainScreen() // Your Screen
     *     }
     * }
     * ```
     * [themeUiState] is provided by [DCodeActivity]
     *
     */
    @Composable
    fun MainContent(themeUiState: ThemeUiState, content: @Composable () -> Unit) {
        val darkTheme = ThemeUtil.shouldUseDarkTheme(themeUiState)

        // Update the edge to edge configuration to match the theme
        // This is the same parameters as the default enableEdgeToEdge call, but we manually
        // resolve whether or not to show dark theme using uiState, since it can be different
        // than the configuration's dark theme value based on the user preference.
        DisposableEffect(darkTheme) {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim = Color.TRANSPARENT,
                    darkScrim = Color.TRANSPARENT,
                ) { darkTheme },
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim = lightScrim,
                    darkScrim = darkScrim,
                ) { darkTheme },
            )
            onDispose {}
        }

        DCodeAppTheme(
            darkTheme = darkTheme,
            themeBrand = ThemeUtil.shouldUseOtherThemeBrand(themeUiState),
            disableGradientColors = ThemeUtil.shouldDisableGradientColors(themeUiState),
        ) {
            content()
        }
    }
}