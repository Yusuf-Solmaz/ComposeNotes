package com.example.unit2diceroller


import android.graphics.fonts.Font
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat

/**
State Hoisting Nedir?
Jetpack Compose'ta state hoisting, bir bileşenin (composable) durumunu dışarıda tutarak, bileşenin
içindeki durum yönetimini üst bileşenlere devretme yöntemidir. Bu yaklaşım, bileşenlerin daha tekrar
kullanılabilir ve test edilebilir olmasını sağlar.

---------------------------------------------------------------------

.statusBarsPadding(): Bu, ekranın üst kısmındaki durum çubuğu (status bar) alanı için otomatik olarak
bir üst boşluk ekler. Böylece içerik, durum çubuğunun altında kalmaz ve üst üste binmez.

.safeDrawingPadding(): Cihazın "çentik" (notch) veya "çerçevesiz" (edge-to-edge) ekranları gibi,
görüntünün sorun yaratabileceği kenar boşluklarını güvenli hale getirir. İçeriği, cihazın fiziksel
kenarlarına göre ayarlar ve çerçevelere göre güvenli bir boşluk bırakır.

.verticalScroll(rememberScrollState()): Bu, Column düzeninin dikey olarak kaydırılabilir hale
gelmesini sağlar. rememberScrollState() ile bir kaydırma durumu (scroll state) oluşturulur, bu sayede
uzun içerikler ekranın boyutunu aşarsa, kullanıcı yukarı-aşağı kaydırarak içeriği gezebilir.


 */

@Composable
fun TipCalculator(modifier: Modifier= Modifier){

    var tipAmount by rememberSaveable{ mutableStateOf("") }
    var amount by rememberSaveable{ mutableStateOf("") }

    tipAmount = calculateTip(amount.toDoubleOrNull() ?: 0.0)

    Column(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start),
            text = stringResource(R.string.calculate_tip)
        )
        Spacer(modifier.size(16.dp))
        CustomAmountTextField(
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth(),
            value = amount,
            onChange = {
                amount = it
            }
        )
        Spacer(modifier.size(16.dp))
        Text(
            text = if (amount == "") "First, enter an amount." else stringResource(R.string.tip_amount, "$tipAmount $"),
            style = TextStyle(
                fontSize = 25.sp
            )
        )
    }
}

private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Composable
fun CustomAmountTextField(
    value: String, // State Hoisting
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_amount),
                contentDescription = ""
            )
        },
        label = {
            Text("Enter Your Amount:")
        },
        onValueChange = onChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true, // kullanıcının input alanını tek bir satır ile sınırlar.
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Black,
            focusedBorderColor = Color.Green
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview(){
    TipCalculator()
}