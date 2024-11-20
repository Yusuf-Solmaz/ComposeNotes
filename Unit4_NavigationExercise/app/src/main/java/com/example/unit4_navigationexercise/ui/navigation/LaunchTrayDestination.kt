package com.example.unit4_navigationexercise.ui.navigation

import androidx.annotation.StringRes
import com.example.unit4_navigationexercise.R

enum class LaunchTrayDestination(@StringRes val title: Int){
    START(title = R.string.start_order),
    ENTREE_MENU(title = R.string.choose_entree),
    SIDE_DISH_MENU(title = R.string.choose_side_dish),
    ACCOMPANIMENT_MENU(title = R.string.choose_accompaniment),
    CHECKOUT(title = R.string.order_checkout)


}