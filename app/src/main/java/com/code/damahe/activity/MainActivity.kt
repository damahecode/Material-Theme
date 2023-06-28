package com.code.damahe.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.code.damahe.theme.theme.DCodeAppTheme
import com.code.damahe.theme.utils.ThemeUtil
import com.code.damahe.theme.viewmodel.ThemeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.code.damahe.theme.viewmodel.ThemeUiState.Loading
import com.code.damahe.theme.viewmodel.ThemeUiState.Success
import com.code.damahe.theme.viewmodel.ThemeUiState
import com.code.damahe.screen.NavMainScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var themeUiState: ThemeUiState by mutableStateOf(Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.themeUiState.onEach {
                    themeUiState = it
                    }
                    .collect()
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            when (themeUiState) {
                Loading -> true
                is Success -> false
            }
        }

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = ThemeUtil.shouldUseDarkTheme(themeUiState)

            // Update the dark content of the system bars to match the theme
            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
                onDispose {}
            }

            DCodeAppTheme(
                darkTheme = darkTheme,
                androidTheme = ThemeUtil.shouldUseAndroidTheme(themeUiState),
                disableDynamicTheming = ThemeUtil.shouldDisableDynamicTheming(themeUiState),
            ) {
                NavMainScreen()
            }
        }
    }
}


