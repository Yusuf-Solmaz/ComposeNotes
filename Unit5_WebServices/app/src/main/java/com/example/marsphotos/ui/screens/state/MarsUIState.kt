package com.example.marsphotos.ui.screens.state

import com.example.marsphotos.data.network.mars_photos.model.MarsPhoto

sealed interface MarsUIState{
    data class Success(val photos: List<MarsPhoto>) : MarsUIState
    data class Error(val error: String) : MarsUIState
    object Loading : MarsUIState
}
