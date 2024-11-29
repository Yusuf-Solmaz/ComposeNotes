package com.example.marsphotos.data.network.mars_photos.repository

import com.example.marsphotos.data.network.mars_photos.model.MarsPhoto

interface MarsPhotosRepository{
    suspend fun getPhotos(): List<MarsPhoto>
}