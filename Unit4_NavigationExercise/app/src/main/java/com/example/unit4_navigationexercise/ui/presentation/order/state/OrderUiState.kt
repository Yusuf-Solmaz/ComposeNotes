package com.example.unit4_navigationexercise.ui.presentation.order.state

import com.example.unit4_navigationexercise.model.MenuItem


data class OrderUiState(
    // Entree Selection
    val entree: MenuItem.EntreeItem? = null,
    val sideDish: MenuItem.SideDishItem? = null,
    val accompaniment: MenuItem.AccompanimentItem? = null,
    val itemTotalPrice: Double = 0.0,
    val orderTax: Double = 0.0,
    val orderTotalPrice: Double = 0.0
)