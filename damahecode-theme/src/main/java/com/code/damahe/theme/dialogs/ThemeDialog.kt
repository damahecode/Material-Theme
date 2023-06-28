package com.code.damahe.theme.dialogs

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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.code.damahe.res.R
import com.code.damahe.theme.config.DarkThemeConfig
import com.code.damahe.theme.config.ThemeBrand
import com.code.damahe.theme.model.UserEditableTheme
import com.code.damahe.theme.theme.supportsDynamicTheming
import com.code.damahe.theme.viewmodel.ThemeViewModel
import com.code.damahe.theme.viewmodel.ThemeUiState

@Composable
fun ThemeDialog(
    onDismiss: () -> Unit,
    viewModel: ThemeViewModel = hiltViewModel()
) {
    val themeUiState by viewModel.themeUiState.collectAsStateWithLifecycle()
    ThemeDialog(
        onDismiss = onDismiss,
        themeUiState = themeUiState,
        onChangeThemeBrand = viewModel::updateThemeBrand,
        onChangeDynamicColorPreference = viewModel::updateDynamicColorPreference,
        onChangeDarkThemeConfig = viewModel::updateDarkThemeConfig,
    )
}

@Composable
fun ThemeDialog(
    themeUiState: ThemeUiState,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onDismiss: () -> Unit,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
) {
    val configuration = LocalConfiguration.current

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(R.string.title_app_theme),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Divider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                when (themeUiState) {
                    ThemeUiState.Loading -> {
                        Text(
                            text = stringResource(R.string.loading),
                            modifier = Modifier.padding(vertical = 16.dp),
                        )
                    }
                    is ThemeUiState.Success -> {
                        ThemePanel(
                            theme = themeUiState.theme,
                            supportDynamicColor = supportDynamicColor,
                            onChangeThemeBrand = onChangeThemeBrand,
                            onChangeDynamicColorPreference = onChangeDynamicColorPreference,
                            onChangeDarkThemeConfig = onChangeDarkThemeConfig,
                        )
                    }
                }
                Divider(Modifier.padding(top = 8.dp))
                //LinksPanel()
            }
            //TrackScreenViewEvent(screenName = "Settings")
        },
        confirmButton = {
            Text(
                text = stringResource(id = R.string.ok),
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
    theme: UserEditableTheme,
    supportDynamicColor: Boolean,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
) {
    //ThemeDialogSectionTitle(text = stringResource(R.string.txt_theme))
    Column(Modifier.selectableGroup()) {
        DialogThemeChooserRow(
            text = stringResource(R.string.brand_default),
            selected = theme.themeBrand == ThemeBrand.DEFAULT,
            onClick = { onChangeThemeBrand(ThemeBrand.DEFAULT) },
        )
        DialogThemeChooserRow(
            text = stringResource(R.string.brand_android),
            selected = theme.themeBrand == ThemeBrand.ANDROID,
            onClick = { onChangeThemeBrand(ThemeBrand.ANDROID) },
        )
    }
    if (theme.themeBrand == ThemeBrand.DEFAULT && supportDynamicColor) {
        ThemeDialogSectionTitle(text = stringResource(R.string.dynamic_color_preference))
        Column(Modifier.selectableGroup()) {
            DialogThemeChooserRow(
                text = stringResource(R.string.dynamic_color_yes),
                selected = theme.useDynamicColor,
                onClick = { onChangeDynamicColorPreference(true) },
            )
            DialogThemeChooserRow(
                text = stringResource(R.string.dynamic_color_no),
                selected = !theme.useDynamicColor,
                onClick = { onChangeDynamicColorPreference(false) },
            )
        }
    }
    ThemeDialogSectionTitle(text = stringResource(R.string.dark_mode_preference))
    Column(Modifier.selectableGroup()) {
        DialogThemeChooserRow(
            text = stringResource(R.string.dark_mode_config_system_default),
            selected = theme.darkThemeConfig == DarkThemeConfig.FOLLOW_SYSTEM,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.FOLLOW_SYSTEM) },
        )
        DialogThemeChooserRow(
            text = stringResource(R.string.dark_mode_config_light),
            selected = theme.darkThemeConfig == DarkThemeConfig.LIGHT,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.LIGHT) },
        )
        DialogThemeChooserRow(
            text = stringResource(R.string.dark_mode_config_dark),
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