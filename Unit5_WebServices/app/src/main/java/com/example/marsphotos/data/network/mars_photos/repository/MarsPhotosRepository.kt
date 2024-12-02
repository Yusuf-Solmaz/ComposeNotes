package com.example.marsphotos.data.network.mars_photos.repository

import com.example.marsphotos.data.network.mars_photos.model.MarsPhoto
import com.example.marsphotos.data.network.mars_photos.result.Result
import kotlinx.coroutines.flow.Flow

interface MarsPhotosRepository{
    fun getPhotos(): Flow<Result<List<MarsPhoto>>>
}