package com.example.unit3_affirmationlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit3_affirmationlist.data.Affirmation
import com.example.unit3_affirmationlist.data.GridDataSource
import com.example.unit3_affirmationlist.ui.theme.Unit3_AffirmationListTheme


/**
 Documantasyona göre modifier'i üste paslamak good practice'dir.

 Örneğin, Modifier.padding(10.dp).fillMaxSize() ifadesinde her Modifier, bir diğerinin üzerine eklenir.
 Bu eklemeyi sağlayan then() fonksiyonudur.

 Modifier'lar birleştirildiğinde arka planda CombinedModifier adında bir nesne oluşturulur. Bu, iki
 farklı Modifier'ı tek bir zincir halinde birleştirir.


 ----------------------------------------------------------------

 Card bileşeninin content'i default olarak Column'dur. Yani eğer content içerisine Column yazılmasa
 bile Column olarak davranır.Bunu override ederek Row da yapılabilir.

 ----------------------------------------------------------------

 Liste görünümü için LazyColumn/LazyRow kullanılır. Bu iki bileşen listeye ekleme yapıldığında sadece
 yeni gelen veriyi recompositiona alır.


 ----------------------------------------------------------------

 Bir vektör çizilebilir dosya ile bir bitmap görüntüsü her ikisi de bir grafiği tanımlasa da aralarında
 önemli farklar vardır.

 Bitmap görüntüler yalnızca her pikseldeki renk bilgilerini saklar ve içerdiği görüntü hakkında daha
 fazla bilgi taşımaz.

 Vektör grafikler ise, şekilleri noktalar, çizgiler ve eğrilerle tanımlar. Bu grafikler her ekran yoğunluğunda
 kalite kaybı olmadan ölçeklenebilir.
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit3_AffirmationListTheme {
                StickyHeaderExample()
            }
        }
    }
}

@Composable
fun TopicGridApp(modifier: Modifier = Modifier){
    val data = GridDataSource.topics
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection),
            ),
    ) {

        VerticalTopicGrid(data)

    }
}

@Composable
fun AffirmationApp(modifier: Modifier = Modifier) {
  //  val data = Datasource().loadAffirmations()
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection),
            ),
    ) {

     //   AffirmationList(affirmationList = data)

    }


}

@Composable
fun AffirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(affirmationList) { affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier= Modifier) {

    Card(
        modifier = modifier,
        onClick = {

        }
    ) {
        Column {
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(affirmation.stringResourceId),
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Unit3_AffirmationListTheme {
        TopicGridApp()
    }
}