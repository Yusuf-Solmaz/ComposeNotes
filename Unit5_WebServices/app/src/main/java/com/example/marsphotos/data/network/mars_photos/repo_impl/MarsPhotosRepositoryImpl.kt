package com.example.marsphotos.data.network.mars_photos.repo_impl

import com.example.marsphotos.data.network.mars_photos.datasource.MarsApiService
import com.example.marsphotos.data.network.mars_photos.model.MarsPhoto
import com.example.marsphotos.data.network.mars_photos.repository.MarsPhotosRepository
import com.example.marsphotos.data.network.mars_photos.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MarsPhotosRepositoryImpl(private val marsApiService: MarsApiService): MarsPhotosRepository {
    override fun getPhotos(): Flow<Result<List<MarsPhoto>>> = flow {
        emit(Result.Loading)
        val result = marsApiService.getPhotos()
        emit(Result.Success(result))
    }.flowOn(Dispatchers.IO)
        .catch {
        error->
        emit(Result.Error(error.localizedMessage ?: "Something went wrong"))
    }


}