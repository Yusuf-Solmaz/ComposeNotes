package com.example.marsphotos

import android.app.Application
import com.example.marsphotos.data.network.mars_photos.datasource.AppContainer
import com.example.marsphotos.data.network.mars_photos.datasource.DefaultAppContainer

class MarsPhotosApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}