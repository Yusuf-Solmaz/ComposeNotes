package com.example.datastoreexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastoreexample.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.datastoreexample.R

@HiltViewModel
class DessertReleaseViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val uiState: StateFlow<DessertReleaseUiState> =
        userPreferencesRepository.isLinearLayout.map { isLinearLayout ->
            DessertReleaseUiState(isLoading = false, isLinearLayout = isLinearLayout)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIME_MILLIS),
            initialValue = DessertReleaseUiState()
        )

    fun selectLayout(isLinearLayout: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveLayoutPreference(isLinearLayout)
        }
    }

    companion object {
        const val STOP_TIME_MILLIS = 5_000L
    }
}


data class DessertReleaseUiState(
    val isLoading:Boolean = true,
    val isLinearLayout: Boolean = true,
    val toggleContentDescription: Int = if (isLinearLayout) R.string.grid_layout_toggle else R.string.linear_layout_toggle,
    val toggleIcon: Int = if (isLinearLayout) R.drawable.ic_grid_layout else R.drawable.ic_linear_layout
)
