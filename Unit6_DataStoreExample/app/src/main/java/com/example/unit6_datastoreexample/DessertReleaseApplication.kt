package com.example.unit6_datastoreexample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DessertReleaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
