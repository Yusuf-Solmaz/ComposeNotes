package com.example.unit1firstandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unit1firstandroidapp.ui.theme.Unit1FirstAndroidAppTheme


/**
@Composable Nedir? ve İsimlendirme Kuralı

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

-----------------------------------------------------------------
@Preview Nedir?

Compose UI'yi gerçek bir cihazda çalıştırmadan, geliştirme aşamasında tasarımı görselleştirmeyi sağlar.
Android Studio'da, bir bileşenin nasıl görüneceğini doğrudan önizlemek ve düzenlemek için kullanılır.

-----------------------------------------------------------------

Scalable Pixels (sp)

Varsayılan olarak SP birimi DP birimiyle aynı boyuttadır ancak telefon ayarları altında kullanıcının
tercih ettiği metin boyutuna göre yeniden boyutlandırılır.

sp fonksiyonu Int, Float ve Double'ın extension fonksiyonudur.

-----------------------------------------------------------------

Good Practice

Modifier'ın Composable'fun kullanıldığı yere iletilmesi iyi bir pratiktir.


----------------------------------------------------------------

Resim Yüklerken Dikkat Edilmesi Gereken Nokta:

Resourca manager ile resim yüklerken fotoğraflar ve arka plan görsellerini,
yeniden boyutlandırma davranışını durduran, çizilebilir-nodpi klasörüne yerleştirmelisiniz. Çünkü
Yani bir cihaz inç kare başına 160 piksele sahipken, başka bir cihaz aynı alana 480 piksel sığdırıyor.
Piksel yoğunluğundaki bu değişiklikleri dikkate almazsanız sistem, görüntülerinizi ölçekleyebilir; bu
da görüntülerin bulanık olmasına, çok fazla bellek tüketen büyük görüntülere veya görüntülerin yanlış
boyutlandırılmasına neden olabilir.


----------------------------------------------------------------

Box Nedir?

Öğeleri birbirinin üzerine yığmak için Box kullanılır. Box düzeni ayrıca içerdiği öğelerin
belirli hizalamasını yapılandırmanıza da olanak tanır.



----------------------------------------------------------------

Image Scale

Resim ölçeklendirirken contentScale (ContentScale) kullanılır. Eğer arguman verilmezse default olarak
ContentScale.Fit kullanılır.

Opaklık: alpha argümanı ile ayarlanır. Default 1.0f'dir. 0 ile 1 arasında değer verilerek opaklık ayarlanır.



----------------------------------------------------------------

String Dosyası Kullanımı

Sabit kodlanmış dize, doğrudan uygulamanızın koduna yazılan dizedir. Sabit kodlanmış dizeler, uygulamanızın
diğer dillere çevrilmesini ve dizelerin uygulamanızın farklı yerlerinde yeniden kullanılmasını zorlaştırır.
 */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit1FirstAndroidAppTheme {

                GreetingText(
                    message = "Yusuf",
                    from = "Ahmet",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                )


            }
        }
    }
}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    Box(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.androidparty),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alpha = 0.6f
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = modifier
            ) {
                Text(
                    stringResource(R.string.happy_birth_day, message),
                    modifier= Modifier
                        .align(alignment = Alignment.CenterHorizontally),
                    fontSize = 30.sp,
                    lineHeight = 30.sp,
                )

                Text(
                    text = stringResource(R.string.from, from),
                    fontSize = 36.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Unit1FirstAndroidAppTheme {
        GreetingText(
            message = stringResource(R.string.yusuf),
            from = stringResource(R.string.ahmet),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        )

    }
}