package com.code.damahe

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * [Application] class for Damahe Code
 */
@HiltAndroidApp
class DCodeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Sync; the system responsible for keeping data in the app up to date.

    }
}