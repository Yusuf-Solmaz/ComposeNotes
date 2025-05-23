package com.example.unit2diceroller

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import kotlin.math.round

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


---------------------------------------------------------------------

Switch Button kullanımında Track: sağa sola haraket eden iconun gezindiği alan, Thumb icon çerçevesi.

---------------------------------------------------------------------

.verticalScroll(rememberScrollState()): Column'un ekranı  kaydırılabilir olmasını sağlar.
.horizontalScroll(rememberScrollState()): Row'un ekranı  kaydırılabilir olmasını sağlar.

---------------------------------------------------------------------

Automated Tests

Uygulamanızda yeni bir özellik oluşturduğunuz her seferde bir test oluşturmak, uygulamanız büyüdükçe
ilerideki iş yükünüzü azaltır.

otomatik testler, yazılım aracılığıyla yürütülen testlerdir; manuel testler ise bir kişinin doğrudan
bir cihazla etkileşimde bulunarak gerçekleştirdiği testlerdir.

Başlangıçta, uygulamanızda gerçekten testlere ihtiyacınız olmadığı gibi görünebilir, ancak test etme,
her boyut ve karmaşıklıktaki uygulamalarda gereklidir.

Type of automated tests

    Local tests: Belirli bir kod parçasını doğrudan test eden otomatik test türleridir. Local testlerle
işlevleri, sınıfları ve özellikleri test edebilirsiniz. Local testler, cihaz veya emülatör gerektirmeden
geliştirme ortamında çalıştırılır, yani bilgisayarınızda çalışır.

    Instrumentation tests: Android geliştirme için, bir Instrumentation testi bir UI testidir.
Instrumentation testleri, Android API'sine ve platform API'lerine ve hizmetlerine bağlı uygulama parçalarını
test etmenizi sağlar.

Yerel testlerin aksine, UI testleri bir uygulamayı veya uygulamanın bir parçasını başlatır, kullanıcı
etkileşimlerini simüle eder ve uygulamanın uygun bir şekilde tepki verip vermediğini kontrol eder.

 * private fonksiyonlar test'e kapalıdır. Bu yüzden test edeceğimiz fonksiyonlar public veya internal olmalı
 internal fonksiyonu testte görünür kılmak için @VisibleForTesting anotasyonu kullanılır.

 */

@Composable
fun TipCalculator(modifier: Modifier= Modifier){

    var tipAmount by rememberSaveable{ mutableStateOf("") }
    var amount by rememberSaveable{ mutableStateOf("") }
    var tipPercentage by rememberSaveable{ mutableStateOf("") }
    var isRoundable by rememberSaveable{ mutableStateOf(false) }

    Log.i("isRoundable",isRoundable.toString())

    tipAmount = calculateTip(
        amount = amount.toDoubleOrNull() ?: 0.0,
        tipPercent = tipPercentage.toDoubleOrNull() ?: 0.0,
        isRoundable = isRoundable
    )

    Column(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()) // ekranı yatayda kaydırılabilir olmasını sağlar.
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
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            value = amount,
            leadingIcon = R.drawable.ic_amount,
            label = R.string.bill_amount,
            onChange = {
                amount = it
            }
        )

        CustomAmountTextField(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            value = tipPercentage,
            leadingIcon = R.drawable.ic_amount,
            label = R.string.tip_percentage,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            onChange = {
                tipPercentage = it
            }
        )
        RoundTheTipRow(
            isRoundable=isRoundable,
            onCheckedChange = {
                isRoundable = it
            },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = if (amount != "" && tipPercentage !="") stringResource(R.string.tip_amount, "$tipAmount $") else stringResource(R.string.fill_the_blanks),
            style = TextStyle(
                fontSize = 25.sp
            )
        )
    }
}

@VisibleForTesting
internal fun calculateTip(isRoundable:Boolean,amount: Double, tipPercent: Double): String {
    val tip = if (isRoundable){
        round(tipPercent / 100 * amount)
    }else{
        tipPercent / 100 * amount
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Composable
fun RoundTheTipRow(isRoundable: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier= Modifier){
    Row(
        modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Round up tip?"
        )

        CustomRoundSwitchTipButton(
            isRoundable = isRoundable,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun CustomRoundSwitchTipButton(
    isRoundable: Boolean,
    onCheckedChange:(Boolean)-> Unit
){
    Switch(
        checked = isRoundable,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedBorderColor = Color.Green,
            uncheckedBorderColor = Color.Black,
            uncheckedThumbColor = Color.White
        )
    )
}


@Composable
fun CustomAmountTextField(
    @DrawableRes leadingIcon: Int,
    @StringRes label: Int, // buraya gelecek değeri Resources içindeki Strings'den geleceğini garantiledik.
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next
    ),
    value: String, // State Hoisting
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIcon),
                contentDescription = ""
            )
        },
        label = {
            Text(text = stringResource(label))
        },
        onValueChange = onChange,
        modifier = modifier,
        keyboardOptions = keyboardOptions, // klavye'nin sağ altındaki "enter" işareti action'ı buradan değiştirilebilir. Default: ImeAction.Done
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