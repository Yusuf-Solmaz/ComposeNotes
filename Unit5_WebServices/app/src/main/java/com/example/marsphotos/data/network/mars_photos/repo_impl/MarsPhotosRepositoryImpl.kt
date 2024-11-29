package com.example.marsphotos.data.network.mars_photos.repo_impl

import com.example.marsphotos.data.network.mars_photos.datasource.MarsApiService
import com.example.marsphotos.data.network.mars_photos.model.MarsPhoto
import com.example.marsphotos.data.network.mars_photos.repository.MarsPhotosRepository

class MarsPhotosRepositoryImpl( private val marsApiService: MarsApiService): MarsPhotosRepository {
    override suspend fun getPhotos(): List<MarsPhoto> = marsApiService.getPhotos()

}