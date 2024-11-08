package com.example.unit4_navigationandapparchitecture

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.unit4_navigationandapparchitecture.data.Datasource
import com.example.unit4_navigationandapparchitecture.model.Dessert
import com.example.unit4_navigationandapparchitecture.ui.theme.Unit4_NavigationAndAppArchitectureTheme


/**
 Bir Android uygulamasında birden fazla aktivite olabilir. Ancak, tek bir aktivite kullanılması önerilmektedir

 -------------------------------

 onCreate() metodu yalnızca bir kez, aktivite ilk kez oluşturulduğunda çağrılır. onStart() metodu ise,
 aktivite her görünür hale geldiğinde çağrılabilir.

 -------------------------------

 onStart ve onResume'da activity görünür haldedir. Sadece onResume'da kullanıcı activity'i ile etkileşime
 girebilir.

 -------------------------------

 onCreate() ve onDestroy(), bir aktivite örneğinin yaşam süresi boyunca yalnızca bir kez çağrılır:
 onCreate() uygulamayı ilk kez başlatmak için ve onDestroy() aktivitenin kullanmakta olduğu nesneleri
 boşaltmak, kapatmak veya yok etmek için çağrılır, böylece bu nesneler kaynakları (örneğin belleği)
 kullanmaya devam etmez.

 -------------------------------

 Uygulamanız arka planda iken, sistem kaynaklarını ve pil ömrünü korumak için aktif olarak çalışmamalıdır.
 Uygulamanız arka plana geçtiğinde devam eden işlemleri duraklatmak, ön plana geçtiğinde ise bu işlemleri
 yeniden başlatmak için Aktivite yaşam döngüsü ve geri çağrılarını kullanırsınız.

 -------------------------------

 Uygulama arka plana alındığında onPause() (focus kaybı) ve onStop() (UI'ın ekranda görünmemesi) metodu çağrılır.
 Aktivite durdurulmuş olsa da Aktivite nesnesi arka planda bellekte tutulur. Android işletim sistemi aktiviteyi kapatmamıştır.
 Kullanıcı uygulamaya geri dönebileceği için Android aktivite kaynaklarını bellekte tutar.

 -------------------------------

 Uygulama tekrar açıldığında onRestart(), onStart() ve onResume() metodu çağrılır. onRestart() metodu,
 aktivitenizin ilk kez başlatılmadığı durumlarda çalıştırmak istediğiniz kodları koyabileceğiniz bir yerdir.

 Aktivite tekrar ön plana döndüğünde onCreate() metodu yeniden çağrılmaz. Aktivite nesnesi yok edilmediği
 için tekrar oluşturulması gerekmez.

 -------------------------------

 Bir uygulama başlatıldığında ve onStart() çağrıldığında, uygulama ekranda görünür hale gelir. onResume()
 çağrıldığında, uygulama kullanıcı odağını kazanır

 Uygulama arka plana geçtiğinde, onPause()’dan sonra odağını kaybeder ve onStop()’tan sonra artık görünür
 olmaz.

 Odağı ve görünürlüğü ayırt etmek önemlidir. Bir aktivite ekranda kısmen görünür olabilir ancak kullanıcı
 odağına sahip olmayabilir.

 -------------------------------

 onPause() içinde çalışan herhangi bir kod diğer şeylerin görüntülenmesini engeller, bu yüzden onPause()
 içindeki kodu hafif tutmak önemlidir. Örneğin, bir telefon araması geldiğinde, onPause()'daki kod gelen
 arama bildirimini geciktirebilir.

 -------------------------------
 */

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate Called")

        setContent {
            Unit4_NavigationAndAppArchitectureTheme() {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                ) {
                    DessertClickerApp(desserts = Datasource.dessertList)
                }
            }
        }
    }
}

/**
 * Hangi tatlının gösterileceğini belirler.
 */
fun determineDessertToShow(
    desserts: List<Dessert>,
    dessertsSold: Int
): Dessert {
    var dessertToShow = desserts.first()
    for (dessert in desserts) {
        if (dessertsSold >= dessert.startProductionAmount) {
            dessertToShow = dessert
        } else {
            // Tatlılar listesi, startProductionAmount'a göre sıralanmıştır. Daha fazla tatlı satıldıkça,
            // daha pahalı tatlılar üretilecektir, bu da startProductionAmount tarafından belirlenir.
            // Satılan tatlı miktarının daha büyük olduğu bir tatlı gördüğümüzde döngüyü durdurmamız gerektiğini biliyoruz.
            break
        }
    }

    return dessertToShow
}

/**
 * Tatlı satış bilgilerini ACTION_SEND intent'i kullanarak paylaşır
 */
private fun shareSoldDessertsInformation(intentContext: Context, dessertsSold: Int, revenue: Int) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            intentContext.getString(R.string.share_text, dessertsSold, revenue)
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)

    try {
        ContextCompat.startActivity(intentContext, shareIntent, null)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            intentContext,
            intentContext.getString(R.string.sharing_not_available),
            Toast.LENGTH_LONG
        ).show()
    }
}

@Composable
private fun DessertClickerApp(
    desserts: List<Dessert>
) {

    var revenue by remember { mutableStateOf(0) }
    var dessertsSold by remember { mutableStateOf(0) }

    val currentDessertIndex by remember { mutableStateOf(0) }

    var currentDessertPrice by remember {
        mutableStateOf(desserts[currentDessertIndex].price)
    }
    var currentDessertImageId by remember {
        mutableStateOf(desserts[currentDessertIndex].imageId)
    }

    Scaffold(
        topBar = {
            val intentContext = LocalContext.current
            val layoutDirection = LocalLayoutDirection.current
            DessertClickerAppBar(
                onShareButtonClicked = {
                    shareSoldDessertsInformation(
                        intentContext = intentContext,
                        dessertsSold = dessertsSold,
                        revenue = revenue
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateStartPadding(layoutDirection),
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(layoutDirection),
                    )
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    ) { contentPadding ->
        DessertClickerScreen(
            revenue = revenue,
            dessertsSold = dessertsSold,
            dessertImageId = currentDessertImageId,
            onDessertClicked = {

                // Geliri günceller
                revenue += currentDessertPrice
                dessertsSold++

                // Sonraki tatlıyı göster
                val dessertToShow = determineDessertToShow(desserts, dessertsSold)
                currentDessertImageId = dessertToShow.imageId
                currentDessertPrice = dessertToShow.price
            },
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Composable
private fun DessertClickerAppBar(
    onShareButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium)),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
        )
        IconButton(
            onClick = onShareButtonClicked,
            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium)),
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(R.string.share),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun DessertClickerScreen(
    revenue: Int,
    dessertsSold: Int,
    @DrawableRes dessertImageId: Int,
    onDessertClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.bakery_back),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(dessertImageId),
                    contentDescription = null,
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.image_size))
                        .height(dimensionResource(R.dimen.image_size))
                        .align(Alignment.Center)
                        .clickable { onDessertClicked() },
                    contentScale = ContentScale.Crop,
                )
            }
            TransactionInfo(
                revenue = revenue,
                dessertsSold = dessertsSold,
                modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
            )
        }
    }
}

@Composable
private fun TransactionInfo(
    revenue: Int,
    dessertsSold: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        DessertsSoldInfo(
            dessertsSold = dessertsSold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
        RevenueInfo(
            revenue = revenue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
private fun RevenueInfo(revenue: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.total_revenue),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = "$${revenue}",
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun DessertsSoldInfo(dessertsSold: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.dessert_sold),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = dessertsSold.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview
@Composable
fun PreviewDessertClickerApp() {
    Unit4_NavigationAndAppArchitectureTheme() {
        DessertClickerApp(desserts = Datasource.dessertList)
    }
}