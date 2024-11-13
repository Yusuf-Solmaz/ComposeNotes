package com.example.unit4_navigation.ui.presentation.navigation

import androidx.annotation.StringRes
import com.example.unit4_navigation.R

// Enum'daki name parametresi Enumun adlandırmasını döndürür. Örneğin CupcakeDestination.START.name -> START
enum class CupcakeDestination(@StringRes val  title: Int) {
    START(title = R.string.start_order_title),
    FLAVOR(title = R.string.choose_flavor),
    PICKUP(title = R.string.choose_pickup_date),
    SUMMARY(title = R.string.order_summary)
}