package com.example.marsphotos.data.network.mars_photos.datasource

import com.example.marsphotos.data.network.mars_photos.repository.MarsPhotosRepository

interface AppContainer{
    val marsPhotosRepository: MarsPhotosRepository
}