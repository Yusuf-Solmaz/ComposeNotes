package com.example.unit4_navigationandapparchitecture.presentation.desert_screen.state
import com.example.unit4_navigationandapparchitecture.R

data class DesertClickerUIState(
    val revenue: Int = 0,
    val desertsSold: Int= 0,
    val currentDesertIndex: Int = 0,
    val currentDessertPrice: Int = 0,
    val currentDesertImageId: Int = R.drawable.cupcake
)