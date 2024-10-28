package com.example.unit2diceroller

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

/**
Jetpack Compose Gestures, kullanıcı etkileşimlerini (dokunma, kaydırma, sürükleme gibi) ele almak için
sağlanan bir API'dir. Gesture (jestler), dokunma ekranlarında yaygın olan hareketlerle (örneğin sürüklemek,
çift tıklamak, yakınlaştırmak) kullanıcı arayüzü bileşenlerine tepki vermeyi sağlar.
 */

@Composable
fun ArtSpace(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ArtImage()
        Spacer(Modifier.size(50.dp))
        ImageDescription()
        PrevNextButtons()
    }
}

@Composable
fun ArtImage(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 450.dp)
            .padding(top = 32.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        Toast.makeText(context, "Still Life of Blue Rose and Other Flowers", Toast.LENGTH_SHORT).show()
                    }
                )
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.lemon_drink),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun ImageDescription(modifier: Modifier = Modifier) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Thin, fontSize = 24.sp)) {
            append("Still Life of Blue Rose and Other Flowers")
        }
        append("\n")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
            append("Owen Scott ")
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
            append("(2021)")
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
fun PrevNextButtons(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomButton(R.string.previous) { }
        CustomButton(R.string.next) { }
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

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpace()
}
