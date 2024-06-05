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

package com.code.damahe.material.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.code.damahe.material.app.DarkThemeConfig
import com.code.damahe.material.app.ThemeBrand
import com.code.damahe.material.app.ThemeConfig.DEVELOPER
import com.code.damahe.material.app.ThemeConfig.DEVELOPER_URL
import com.code.damahe.material.app.ThemeConfig.LIBRARY_NAME
import com.code.damahe.material.app.ThemeConfig.MATERIAL_THEME_GITHUB
import com.code.damahe.material.model.ThemeString
import com.code.damahe.material.model.UserEditableTheme
import com.code.damahe.material.theme.supportsDynamicTheming
import com.code.damahe.material.viewmodel.ThemeViewModel
import com.code.damahe.material.viewmodel.ThemeUiState

@Composable
fun ThemeDialog(
    string: ThemeString = ThemeString(),
    onDismiss: () -> Unit,
    viewModel: ThemeViewModel = hiltViewModel()
) {
    val themeUiState by viewModel.themeUiState.collectAsStateWithLifecycle()
    ThemeDialog(
        string = string,
        onDismiss = onDismiss,
        themeUiState = themeUiState,
        onChangeThemeBrand = viewModel::updateThemeBrand,
        onChangeGradientColorsPreference = viewModel::updateGradientColorsPreference,
        onChangeDarkThemeConfig = viewModel::updateDarkThemeConfig,
    )
}

@Composable
fun ThemeDialog(
    string: ThemeString,
    themeUiState: ThemeUiState,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onDismiss: () -> Unit,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeGradientColorsPreference: (useGradientColors: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
) {
    val configuration = LocalConfiguration.current

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = string.title,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                HorizontalDivider()
                when (themeUiState) {
                    ThemeUiState.Loading -> {
                        Text(
                            text = string.loading,
                            modifier = Modifier.padding(vertical = 16.dp),
                        )
                    }
                    is ThemeUiState.Success -> {
                        ThemePanel(
                            string = string,
                            theme = themeUiState.theme,
                            supportDynamicColor = supportDynamicColor,
                            onChangeThemeBrand = onChangeThemeBrand,
                            onChangeGradientColorsPreference = onChangeGradientColorsPreference,
                            onChangeDarkThemeConfig = onChangeDarkThemeConfig,
                        )
                    }
                }
                HorizontalDivider(Modifier.padding(top = 8.dp))
                LinksPanel()
            }
        },
        confirmButton = {
            Text(
                text = string.ok,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() },
            )
        }
    )
}

@Composable
private fun ThemePanel(
    string: ThemeString,
    theme: UserEditableTheme,
    supportDynamicColor: Boolean,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeGradientColorsPreference: (useGradientColors: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
) {
    Column(Modifier.selectableGroup()) {
        DialogThemeChooserRow(
            text = string.brandDefault,
            selected = theme.themeBrand == ThemeBrand.DEFAULT,
            onClick = { onChangeThemeBrand(ThemeBrand.DEFAULT) },
        )
        if (supportDynamicColor) {
            DialogThemeChooserRow(
                text = string.brandDynamic,
                selected = theme.themeBrand == ThemeBrand.DYNAMIC,
                onClick = { onChangeThemeBrand(ThemeBrand.DYNAMIC) },
            )
        }
    }
    ThemeDialogSectionTitle(text = string.useGradientColors)
    Column(Modifier.selectableGroup()) {
        DialogThemeChooserRow(
            text = string.yes,
            selected = theme.useGradientColors,
            onClick = { onChangeGradientColorsPreference(true) },
        )
        DialogThemeChooserRow(
            text = string.no,
            selected = !theme.useGradientColors,
            onClick = { onChangeGradientColorsPreference(false) },
        )
    }
    ThemeDialogSectionTitle(text = string.darkModePreference)
    Column(Modifier.selectableGroup()) {
        DialogThemeChooserRow(
            text = string.systemDefault,
            selected = theme.darkThemeConfig == DarkThemeConfig.FOLLOW_SYSTEM,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.FOLLOW_SYSTEM) },
        )
        DialogThemeChooserRow(
            text = string.light,
            selected = theme.darkThemeConfig == DarkThemeConfig.LIGHT,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.LIGHT) },
        )
        DialogThemeChooserRow(
            text = string.dark,
            selected = theme.darkThemeConfig == DarkThemeConfig.DARK,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.DARK) },
        )
    }
}

@Composable
private fun ThemeDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
fun DialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
private fun LinksPanel() {
    Row(
        modifier = Modifier.padding(top = 16.dp),
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row {
                TextLink(
                    text = DEVELOPER,
                    url = DEVELOPER_URL,
                )
                Spacer(Modifier.width(8.dp))
                TextLink(
                    text = LIBRARY_NAME,
                    url = MATERIAL_THEME_GITHUB,
                )
            }
        }
    }
}

@Composable
private fun TextLink(text: String, url: String) {
    val uriHandler = LocalUriHandler.current

    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable { uriHandler.openUri(url) },
    )
}