package com.example.unit2diceroller

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.unit2diceroller.ui.theme.Unit2DiceRollerTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat


/**
 * Derleyici, androidTest dizinindeki @Test notasyonu ile açıklanmış yöntemlerin enstrümantasyon
 testlerine, test dizinindeki @Test notasyonu ile açıklanmış yöntemlerin ise yerel testlere atıfta bulunduğunu bilir.

 */

class TipUITests {

    @get:Rule // Unit tarafından test kurallarını belirtmek için kullanılan bir anotasyon.
    val composeTestRule = createComposeRule() // Compose UI testlerini gerçekleştirmek için bir kural oluşturuyoruz.

    @Test
    fun calculate_20_percent_tip() {
        composeTestRule.setContent {
            Unit2DiceRollerTheme {
                TipCalculator()
            }
        }
            composeTestRule.onNodeWithText("Bill Amount:").performTextInput("10.0") //"Bill Amount:" metnini içeren UI bileşenine erişim sağlıyoruz ve metin kutusuna "10.0" yazıyoruz.
            composeTestRule.onNodeWithText("Tip Percentage:").performTextInput("20.0")

            val expectedTip = NumberFormat.getCurrencyInstance().format(2)

            composeTestRule.onNodeWithText("Tip Amount: $expectedTip $").assertExists(  //"Tip Amount: $2.00" metnini içeren UI bileşeninin varlığını doğruluyoruz.
                "No node with this text was found." // Eğer bu metne sahip bir düğüm bulunamazsa, bu hata mesajı gösterilecek.
            )

    }
}