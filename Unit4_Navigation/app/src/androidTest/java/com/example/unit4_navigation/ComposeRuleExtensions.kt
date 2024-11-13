package com.example.unit4_navigation

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule

/**
Bu genişletme fonksiyonu, bir kullanıcı arayüzü bileşenini kaynak dizesiyle bulurken yazmanız gereken
kod miktarını azaltmanıza olanak tanır. Bu kodu yazmak yerine:

composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.my_string)
 */

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))