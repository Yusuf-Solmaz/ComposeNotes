package com.example.unit4_navigation.data
import com.example.unit4_navigation.R

object DataSource {
    val flavors = listOf(
        Pair(R.string.vanilla, 2.00),
        Pair(R.string.chocolate, 2.50),
        Pair(R.string.red_velvet, 3.00),
        Pair(R.string.salted_caramel, 2.75),
        Pair(R.string.coffee, 3.25)
    )

    val quantityOptions = listOf(
        Pair(R.string.one_cupcake, 1),
        Pair(R.string.six_cupcakes, 6),
        Pair(R.string.twelve_cupcakes, 12)
    )
}