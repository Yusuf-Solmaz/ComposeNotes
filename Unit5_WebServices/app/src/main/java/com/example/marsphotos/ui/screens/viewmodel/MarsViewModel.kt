/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.data.network.mars_photos.model.MarsPhoto
import com.example.marsphotos.data.network.mars_photos.repository.MarsPhotosRepository
import com.example.marsphotos.ui.screens.state.MarsUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/*
ViewModelScope, uygulamanızdaki her ViewModel için tanımlanmış yerleşik bir coroutine scope'dur.
Bu scope içinde başlatılan herhangi bir coroutine, ViewModel temizlendiğinde (cleared) otomatik
olarak iptal edilir.

Web servisi isteğini arka planda yapmak ve coroutine başlatmak için viewModelScope kullanabilirsiniz.
ViewModelScope, ViewModel'e ait olduğundan, uygulama bir yapılandırma değişikliğinden geçse bile istek devam eder.
*/

class MarsViewModel (val photoRepo: MarsPhotosRepository): ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository)
            }
        }
    }

    private val _marsUiState = MutableStateFlow<MarsUIState>(MarsUIState.Loading)
    val marsUiState: StateFlow<MarsUIState> get() = _marsUiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val allPhotos = mutableListOf<MarsPhoto>() // Bütün yüklenen veriler
    private var currentPage = 1
    private val pageSize = 20 // Bir sayfadaki öğe sayısı

    init {
        getAllPhotos()
    }

    fun getAllPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            _marsUiState.value = MarsUIState.Loading
            try {
                val result = photoRepo.getPhotos() // Tüm veriyi tek seferde alıyoruz
                allPhotos.addAll(result)
                loadPage(1) // İlk sayfayı yükle
            } catch (e: Exception) {
                _marsUiState.value = MarsUIState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun loadPage(page: Int) {
        viewModelScope.launch {
            if (page > 0 && (page - 1) * pageSize < allPhotos.size) {
                _isLoading.value = true // Yükleniyor durumunu başlat
                val start = (page - 1) * pageSize
                val end = minOf(start + pageSize, allPhotos.size)
                val newPhotos = allPhotos.subList(start, end)

                // Yeni verileri önceki listeye ekle
                val currentPhotos = (marsUiState.value as? MarsUIState.Success)?.photos ?: emptyList()
                _marsUiState.value = MarsUIState.Success(currentPhotos + newPhotos)

                currentPage = page
                _isLoading.value = false // Yükleniyor durumunu kapat
            }
        }
    }

    fun loadNextPage() {
        if (!_isLoading.value) { // Hali hazırda yükleniyorsa yeni bir yükleme başlatma
            loadPage(currentPage + 1)
        }
    }
}
