package com.example.marsphotos.data.network.mars_photos.datasource

import com.example.marsphotos.data.network.mars_photos.model.MarsPhoto
import retrofit2.http.GET

interface MarsApiService{
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}