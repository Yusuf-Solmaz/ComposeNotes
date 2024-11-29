package com.example.marsphotos.data.network.mars_photos.datasource

import com.example.marsphotos.data.network.mars_photos.repo_impl.MarsPhotosRepositoryImpl
import com.example.marsphotos.data.network.mars_photos.repository.MarsPhotosRepository
import com.example.marsphotos.data.network.mars_photos.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 Singleton deseni önerilen bir uygulama değildir. Singleton'lar, özellikle testlerde öngörülmesi zor
 olan küresel durumları (global states) temsil eder. Nesneler, ihtiyaç duydukları bağımlılıkları tanımlamalıdır; bu bağımlılıkların nasıl oluşturulacağını tanımlamak yerine.

 Singleton deseni yerine Dependency Injection kullanılmalı.
*/

class DefaultAppContainer : AppContainer{
    override val marsPhotosRepository: MarsPhotosRepository by lazy {
        MarsPhotosRepositoryImpl(retrofitService)
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}



