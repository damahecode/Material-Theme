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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.code.damahe.material.theme.DCodeAppTheme
import com.code.damahe.material.utils.ThemeUtil
import com.code.damahe.material.viewmodel.ThemeUiState
import com.code.damahe.material.viewmodel.ThemeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
                viewModel.themeUiState.onEach {
                    themeUiState = it
                }
                    .collect()
            }
        }
    }
}

/**
 * Your Main Composable Function here
 * ```
 * setContent {
 *     MainContent()
 * }
 * ```
 */
@Composable
fun MainContent(themeUiState: ThemeUiState, content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    val darkTheme = ThemeUtil.shouldUseDarkTheme(themeUiState)

    // Update the dark content of the system bars to match the theme
    DisposableEffect(systemUiController, darkTheme) {
        systemUiController.systemBarsDarkContentEnabled = !darkTheme
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