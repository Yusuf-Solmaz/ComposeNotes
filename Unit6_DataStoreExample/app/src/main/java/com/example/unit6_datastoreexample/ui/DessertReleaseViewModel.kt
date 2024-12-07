/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.unit6_datastoreexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unit6_datastoreexample.R
import com.example.unit6_datastoreexample.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * View model of Dessert Release components
 */
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

/*
 * Data class containing various UI States for Dessert Release screens
 */
data class DessertReleaseUiState(
    val isLoading:Boolean = true,
    val isLinearLayout: Boolean = true,
    val toggleContentDescription: Int = if (isLinearLayout) R.string.grid_layout_toggle else R.string.linear_layout_toggle,
    val toggleIcon: Int = if (isLinearLayout) R.drawable.ic_grid_layout else R.drawable.ic_linear_layout
)
