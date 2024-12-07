package com.example.dessertrelease

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.dessertrelease.data.UserPreferencesRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DessertReleaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
