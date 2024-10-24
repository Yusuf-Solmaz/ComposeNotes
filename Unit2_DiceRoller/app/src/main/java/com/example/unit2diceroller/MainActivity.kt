package com.example.unit2diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit2diceroller.ui.theme.Unit2DiceRollerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit2DiceRollerTheme {

                DiceRollerApp()

            }
        }
    }
}

/**
 Hizalama:

 horizontalAlignment, verticalArrangement, horizontalArrangement ve verticalAlignment
 Column ve  Row içindeki elemanları hizalar. Modifier'daki wrapContentSize() ise sadece
 bir bileşenin içerdiği şeyi hizalar.

 ---------------------------------------------------------------------

 Compose'da neden remember kullanılır?

 Composable'lar varsayılan olarak stateless (durumsuz) oldukları için bir değer tutmazlar
 ve sistem tarafından herhangi bir zamanda yeniden oluşturulabilirler, bu da değerin sıfırlanmasına
 neden olur.

 Örneğin butona her tıklandığında sayının 1 artmasını istiyoruz fakat değişkeni var ile tanımladığık.
 Bu durumda sistem Composable yeniden oluşturulur ve sayı sıfırlanır. Bu durumlar için Compose
 uygun bir yol sunar. Composable fonksiyonlar, remember composable'ı kullanarak bellekte bir nesne
 saklayabilirler.

 ---------------------------------------------------------------------

 mutableStateOf(): fonksiyonu bir gözlemlenebilir (observable) döndürür. Ve değer değiştiğinde
 bu değeri kullanan tüm bileşenler yeniden oluşturulur.
 */

@Composable
fun DiceRollerApp(modifier: Modifier= Modifier) {
    DiceWithButtonAndImage()
}

@Composable
fun DiceWithButtonAndImage(
    modifier:Modifier = Modifier){
    var result by rememberSaveable { mutableIntStateOf(1) }

    // result her değiştiğinde bileşen yeniden oluşturulur.
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Column(
        modifier=modifier
            .fillMaxSize(),
            //.wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = ""
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = {
                result = (1..6).random()
            }
        ) {
            Text(text = stringResource(R.string.roll))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Unit2DiceRollerTheme {
        DiceRollerApp()
    }
}