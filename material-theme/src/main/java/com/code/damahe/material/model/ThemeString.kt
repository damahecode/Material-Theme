package com.code.damahe.material.model

import androidx.annotation.StringRes

data class ThemeString(
    @StringRes val title: Int,
    @StringRes val loading: Int,
    @StringRes val ok: Int,
    @StringRes val brandDefault: Int,
    @StringRes val brandAndroid: Int,
    @StringRes val useDynamicColor: Int,
    @StringRes val yes: Int,
    @StringRes val no: Int,
    @StringRes val darkModePreference: Int,
    @StringRes val systemDefault: Int,
    @StringRes val configLight: Int,
    @StringRes val configDark: Int,
)
