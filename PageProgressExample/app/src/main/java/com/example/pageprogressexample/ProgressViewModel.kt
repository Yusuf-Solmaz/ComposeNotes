package com.example.pageprogressexample

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProgressViewModel : ViewModel() {
    private val _uiState = mutableStateOf(ProgressUiState())
    val uiState: State<ProgressUiState> = _uiState

    // Seçim güncelleme fonksiyonu
    fun updateSelection(category: Category? = null, ageGroup: AgeGroup? = null, gender: Gender? = null) {
        _uiState.value = _uiState.value.copy(
            categorySelection = category ?: _uiState.value.categorySelection,
            ageGroupSelection = ageGroup ?: _uiState.value.ageGroupSelection,
            genderSelection = gender ?: _uiState.value.genderSelection
        )
    }
}

data class ProgressUiState(
    val categorySelection: Category? = null,
    val ageGroupSelection: AgeGroup? = null,
    val genderSelection: Gender? = null
)
