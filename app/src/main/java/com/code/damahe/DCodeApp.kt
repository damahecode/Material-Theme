package com.code.damahe

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The main [Application] class for the Damahe Code application.
 *
 * This class initializes the application and sets up the Hilt dependency injection framework.
 * It serves as the application's entry point and is responsible for managing the application's
 * global state and resources.
 *
 * The `@HiltAndroidApp` annotation triggers Hilt's code generation, creating the necessary
 * components for dependency injection throughout the application.
 */
@HiltAndroidApp
class DCodeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialization logic can be added here if needed.
    }
}