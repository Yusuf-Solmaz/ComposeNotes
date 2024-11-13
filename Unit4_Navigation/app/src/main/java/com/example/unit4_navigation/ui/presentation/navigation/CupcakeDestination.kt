package com.example.unit4_navigation.ui.presentation.navigation

import androidx.annotation.StringRes
import com.example.unit4_navigation.R

// Enum'daki name parametresi Enumun adlandırmasını döndürür. Örneğin CupcakeDestination.START.name -> START
enum class CupcakeDestination(@StringRes val route: Int, @StringRes val  title: Int) {
    START(route = R.string.app_name, title = R.string.start_order_title),
    FLAVOR(route = R.string.choose_flavor, title = R.string.choose_flavor),
    PICKUP(route = R.string.choose_pickup_date, title = R.string.choose_pickup_date),
    SUMMARY(route = R.string.order_summary, title = R.string.order_summary)
}