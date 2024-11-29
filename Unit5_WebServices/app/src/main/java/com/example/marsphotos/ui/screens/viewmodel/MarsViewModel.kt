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

    private val _marsUiState = MutableStateFlow<MarsUIState>(MarsUIState.Loading)
    val marsUiState: StateFlow<MarsUIState>
        get() = _marsUiState


    init {
        getMarsPhotos()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository)
            }
        }
    }

    private fun getMarsPhotos() {
        viewModelScope.launch(Dispatchers.IO){
            _marsUiState.value = MarsUIState.Loading
            try {
                val result = photoRepo.getPhotos()
                _marsUiState.value = MarsUIState.Success("Success: ${result.size} Mars photos retrieved")
            }
            catch (e: Exception){
                _marsUiState.value = MarsUIState.Error(e.message!!)
            }

        }
    }
}
