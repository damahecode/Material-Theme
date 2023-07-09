package com.code.damahe.material.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.damahe.material.config.DarkThemeConfig
import com.code.damahe.material.config.ThemeBrand
import com.code.damahe.material.data.ThemeDataRepository
import com.code.damahe.material.model.UserEditableTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeDataRepository: ThemeDataRepository,
) : ViewModel() {

    val themeUiState: StateFlow<ThemeUiState> = themeDataRepository.userEditableTheme.map {
        ThemeUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = ThemeUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

    fun updateThemeBrand(themeBrand: ThemeBrand) {
        viewModelScope.launch {
            themeDataRepository.setThemeBrand(themeBrand)
        }
    }

    fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            themeDataRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    fun updateDynamicColorPreference(useDynamicColor: Boolean) {
        viewModelScope.launch {
            themeDataRepository.setDynamicColorPreference(useDynamicColor)
        }
    }
}

sealed interface ThemeUiState {
    object Loading : ThemeUiState
    data class Success(val theme: UserEditableTheme) : ThemeUiState
}