package com.example.unit2diceroller

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource


@Composable
fun Lemonade(modifier: Modifier = Modifier){

    var imageId by remember { mutableIntStateOf(1) }
    var squezeNumber by remember { mutableIntStateOf(0) }
    var requiredSquezeNumber = remember { (2..4).random() }
    var textId by remember { mutableStateOf("") }

    var imageResource = when (imageId){
        1->{
            textId = stringResource(R.string.lemon_tree_select)
            R.drawable.lemon_tree
        }
        2->{
            textId = stringResource(R.string.keep_tapping)
            R.drawable.lemon_squeeze
        }
        3->{
            textId = stringResource(R.string.tap_lemonade)
            R.drawable.lemon_drink
        }
        else -> {
            textId = stringResource(R.string.tap_empty_glass)
            R.drawable.lemon_restart}
    }


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.background(Color(0xFFFFC107)).fillMaxWidth().height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Lemonade"
            )
        }
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = null,
                modifier = Modifier.width(200.dp).height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = textId,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
                    .clickable{
                        when (imageId) {
                            1 -> {
                                imageId++
                            }
                            2 -> {
                                if (squezeNumber < requiredSquezeNumber) {
                                    squezeNumber++
                                } else {
                                    imageId++
                                    requiredSquezeNumber = (2..4).random()
                                    squezeNumber = 0
                                }
                            }
                            3 -> {
                                imageId++
                            }
                            4 -> {
                                imageId = 1
                            }
                        }
                        Log.i("infoImageId",imageId.toString())
                        Log.i("infoSquezeNumber",squezeNumber.toString())
                        Log.i("infoRequiredSquezeNumber",requiredSquezeNumber.toString())
                    }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun LemonadePreview(){
    Lemonade()
}