package com.example.marsphotos.data.network.mars_photos.result

sealed interface Result<out R> {
    data class Success<out R>(val data: R?) : Result<R>
    data class Error(val message:String) : Result<Nothing>
    data object Loading : Result<Nothing>
}