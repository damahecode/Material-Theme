package com.code.damahe.theme.data

import android.content.Context
import com.code.damahe.theme.config.DarkThemeConfig
import com.code.damahe.theme.config.ThemeBrand
import com.code.damahe.theme.utils.DataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeDataRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    //private val context = WeakReference(context)

    private val dataStoreManager = DataStoreManager(context)

    val userEditableTheme = dataStoreManager.getThemeFromDataStore()

    suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        dataStoreManager.saveThemeBrandDataStore(themeBrand)
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        dataStoreManager.saveDarkThemeConfigDataStore(darkThemeConfig)
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        dataStoreManager.saveDynamicColorPreferenceDataStore(useDynamicColor)
    }
}