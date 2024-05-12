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

package com.code.damahe.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code.damahe.res.R
import com.code.damahe.res.icon.DCodeIcon.ImageVectorIcon
import com.code.damahe.res.icon.MyIcons
import com.code.damahe.material.theme.DCodeBackground
import com.code.damahe.material.theme.DCodeGradientBackground
import com.code.damahe.material.dialogs.ThemeDialog
import com.code.damahe.material.model.ThemeString
import com.code.damahe.material.theme.GradientColors
import com.code.damahe.material.theme.LocalGradientColors
import com.code.damahe.res.icon.DrawIcon

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun MainScreen() {

    val context = LocalContext.current

    val showThemeSettingsDialog = remember { mutableStateOf(false) }
    val shouldShowGradientBackground = true //TODO : add method

    if (showThemeSettingsDialog.value) {
        ThemeDialog(
            string = ThemeString(R.string.title_app_theme, R.string.loading, R.string.ok, R.string.brand_default,
                R.string.brand_dynamic, R.string.gradient_colors_preference, R.string.gradient_colors_yes,
                R.string.gradient_colors_no, R.string.dark_mode_preference, R.string.dark_mode_config_system_default,
                R.string.dark_mode_config_light, R.string.dark_mode_config_dark),
            onDismiss = {showThemeSettingsDialog.value = false},
        )
    }

    DCodeBackground {
        DCodeGradientBackground(
            gradientColors = if (shouldShowGradientBackground) {
                LocalGradientColors.current
            } else {
                GradientColors()
            }
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                topBar = {
                    TopAppBar(
                        title = { Text(text = stringResource(id = R.string.app_name)) },
                        navigationIcon = {
                            IconButton(onClick = { showThemeSettingsDialog.value = true }
                            ) {
                                DrawIcon(icon = ImageVectorIcon(MyIcons.Settings), contentDescription = stringResource(id = R.string.txt_preferences))
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent,
                        ),
                    )
                },
            ) { padding ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .systemBarsPadding()
                            .padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = stringResource(id = R.string.app_name))
                        // on below line adding a spacer.
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(onClick = {
                            val urlIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/damahecode/Material-Theme")
                            )
                            context.startActivity(urlIntent)
                        }) {
                            Text(
                                text = stringResource(id = R.string.txt_github),
                                modifier = Modifier.padding(10.dp),
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}