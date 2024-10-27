package com.example.unit2diceroller

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.NumberFormat

/**
 * Otomatik testleri yöntemler (metotlar) şeklinde yazılır.
 @Test anotasyonuyla işaretlenir. Bu, derleyiciye yöntemin bir test yöntemi olduğunu bildirir ve
 yöntemi buna göre çalıştırır.

 * Test yöntemleri, normal uygulama yöntemleri gibi mantık kullanmazlar. Nasıl uygulandığını
 önemsemezler; yalnızca belirli bir girdiye karşılık beklenen bir çıktıyı kontrol ederler.
 */


class TipCalculatorTests {

    // Tip hesaplama fonksiyonu için bir test. (20% tip for a $10 bill amount)

    @Test
    fun calculateTip_20PercentNoRoundup() {

        val amount = 10.0
        val tipPercent = 20.0
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTip(amount = amount, tipPercent = tipPercent, isRoundable = false)
        assertEquals(expectedTip,actualTip)
    }
}