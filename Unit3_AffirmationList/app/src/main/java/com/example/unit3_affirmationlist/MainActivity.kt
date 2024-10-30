package com.example.unit3_affirmationlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit3_affirmationlist.ui.theme.Unit3_AffirmationListTheme


/**
 Documantasyona göre modifier'i üste paslamak good practice'dir.

 Örneğin, Modifier.padding(10.dp).fillMaxSize() ifadesinde her Modifier, bir diğerinin üzerine eklenir.
 Bu eklemeyi sağlayan then() fonksiyonudur.

 Modifier'lar birleştirildiğinde arka planda CombinedModifier adında bir nesne oluşturulur. Bu, iki
 farklı Modifier'ı tek bir zincir halinde birleştirir.

 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit3_AffirmationListTheme {
                Greeting(
                    name = "Android",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Hello $name!",
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Unit3_AffirmationListTheme {
        Greeting("Android")
    }
}