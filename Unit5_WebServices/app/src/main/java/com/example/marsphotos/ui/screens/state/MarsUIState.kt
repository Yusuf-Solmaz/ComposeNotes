package com.example.marsphotos.ui.screens.state

sealed interface MarsUIState{
    data class Success(val photos: String) : MarsUIState
    data class Error(val error: String) : MarsUIState
    object Loading : MarsUIState
}
