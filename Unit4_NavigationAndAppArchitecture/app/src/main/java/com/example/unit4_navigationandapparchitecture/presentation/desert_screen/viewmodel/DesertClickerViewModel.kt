package com.example.unit4_navigationandapparchitecture.presentation.desert_screen.viewmodel

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.unit4_navigationandapparchitecture.R
import com.example.unit4_navigationandapparchitecture.model.Dessert
import com.example.unit4_navigationandapparchitecture.presentation.desert_screen.state.DesertClickerUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DesertClickerViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(DesertClickerUIState())
    val desertState: StateFlow<DesertClickerUIState> get() = _uiState.asStateFlow()

    // Handle dessert click (updating revenue, sold desserts, and dessert state)
    fun onDessertClicked(desserts: List<Dessert>, dessertsSold: Int) {
        // Increase revenue and sold desserts
        val updatedRevenue = _uiState.value.revenue + _uiState.value.currentDessertPrice
        val updatedDessertsSold = dessertsSold + 1

        // Determine the next dessert to show based on sold desserts
        val dessertToShow = determineDessertToShow(desserts, updatedDessertsSold)

        // Update the UI state
        _uiState.value = _uiState.value.copy(
            revenue = updatedRevenue,
            desertsSold = updatedDessertsSold,
            currentDesertImageId = dessertToShow.imageId,
            currentDessertPrice = dessertToShow.price
        )
    }

    // Determine which dessert to show based on sold amount
    fun determineDessertToShow(
        desserts: List<Dessert>,
        dessertsSold: Int
    ): Dessert {
        var dessertToShow = desserts.first()
        for (dessert in desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                break
            }
        }
        return dessertToShow
    }

    // Share information about sold desserts
    fun shareSoldDessertsInformation(intentContext: Context, dessertsSold: Int, revenue: Int) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                intentContext.getString(R.string.share_text, dessertsSold, revenue)
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)

        try {
            intentContext.startActivity(shareIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                intentContext,
                intentContext.getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
