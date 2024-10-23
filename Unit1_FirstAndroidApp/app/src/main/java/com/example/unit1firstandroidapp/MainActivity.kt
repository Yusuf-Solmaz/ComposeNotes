package com.example.unit1firstandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit1firstandroidapp.ui.theme.Unit1FirstAndroidAppTheme



/**
 @Composable fun UI'ın nasıl görüneceğini söyler. Compose bu fonksiyonları kullanarak UI'ı oluşturur.
 Compose'da her UI öğesi bir @Composable fonksiyon olarak tanımlanır. Bu fonksiyoları tanımlarken Kotlin
 kullanılır.

 @Composable fun'lar stateless'dir ve herhangi bir değer geri döndürmezler (Unit).

 Bir @Composabele fun immutable'dir. Eğer data değiştiğinde UI refresh olması gerekiyorsa Compose otomatik
 olarak @Composable fun'ı re-execute eder. Böylece sayfanın sadece değiştiği kısımlarını günceller.

 @Composable fun isimlendirmesi Pascal case ve noun olmalıdır. Çünkü Bir composable'ın varlığı veya yokluğu,
 onu çağıran kontrol akışının değerlendirilmesi sonucunda belirlenir ve bu, hem yeniden oluşturmalarda kalıcı
 bir kimlik sağlar hem de bu kalıcı kimlik için bir yaşam döngüsü oluşturur. Bu adlandırma kuralı, bu
 bildirimsel zihinsel modeli teşvik eder ve güçlendirir.
 (İsimlendirme ile ilgili daha fazla bilgi için: https://github.com/androidx/androidx/blob/androidx-main/compose/docs/compose-api-guidelines.md#naming-unit-composable-functions-as-entities)

 */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit1FirstAndroidAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Unit1FirstAndroidAppTheme {
        Greeting("Android")
    }
}