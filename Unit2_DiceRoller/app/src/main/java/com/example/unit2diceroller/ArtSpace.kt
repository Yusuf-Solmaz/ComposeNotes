package com.example.unit2diceroller

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


/**
Jetpack Compose Gestures, kullanıcı etkileşimlerini (dokunma, kaydırma, sürükleme gibi) ele almak için
sağlanan bir API'dir. Gesture (jestler), dokunma ekranlarında yaygın olan hareketlerle (örneğin sürüklemek,
çift tıklamak, yakınlaştırmak) kullanıcı arayüzü bileşenlerine tepki vermeyi sağlar.
 */

@Composable
fun ArtSpace(modifier: Modifier = Modifier) {

    val artList = listOf<Art>(
        Art(R.drawable.lemon_drink,"Still Life of Blue Rose and Other Flowers","Owen Scott (1)","2023"),
        Art(R.drawable.lemon_restart,"Still Life","Owen Scott (2)","2023"),
        Art(R.drawable.lemon_squeeze,"Still Life of Blue Rose","Owen Scott (3)","2023"),
    )
    var artListState by remember {
        mutableIntStateOf(0)
    }

    val goToNext : () -> Unit = {
        if (artListState < artList.size - 1) {
            artListState++
        } else {
            artListState = 0
        }
    }


    val goToPrev: () -> Unit = {
        if (artListState > 0) {
            artListState--
        } else {
            artListState = artList.size - 1
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ArtImage(
            art = artList[artListState],
            onNext = goToNext,
            onPrev = goToPrev
        )
        Spacer(Modifier.size(50.dp))
        ImageDescription(art = artList[artListState])
        PrevNextButtons(
            onPrevClick = goToNext,
            onNextClick = goToPrev
        )
    }
}

@Composable
fun ArtImage(art: Art, modifier: Modifier = Modifier, onNext: () -> Unit, onPrev: () -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 450.dp)
            .padding(top = 32.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    // Sürükleme algılaması için bir eşik tanımlıyoruz.
                    val threshold = 50.dp.toPx()
                    if (dragAmount.x > threshold) {  // Sağa sürüklendi
                        onNext()
                        change.consume()
                    } else if (dragAmount.x < -threshold) {  // Sola sürüklendi
                        onPrev()
                        change.consume()
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        Toast.makeText(context, art.imageDescription, Toast.LENGTH_SHORT).show()
                    }
                )
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(art.image),
                contentDescription = null
            )
        }
    }
}

@Composable
fun ImageDescription(art: Art,modifier: Modifier = Modifier) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Thin, fontSize = 24.sp)) {
            append(art.imageDescription)
        }
        append("\n")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
            append(art.artistName)
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
            append(art.artYear)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(4.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = annotatedString, Modifier.padding(16.dp))
    }
}

@Composable
fun PrevNextButtons(modifier: Modifier = Modifier,onPrevClick: () -> Unit,onNextClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomButton(text = R.string.previous, onClick = onPrevClick)
        CustomButton(text = R.string.next, onClick = onNextClick)
    }
}

@Composable
fun CustomButton(@StringRes text: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray,
            contentColor = Color.White
        )
    ) {
        Text(text = stringResource(text))
    }
}

data class Art(@DrawableRes val image:Int,val imageDescription: String,val artistName: String,val artYear: String)

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpace()
}
