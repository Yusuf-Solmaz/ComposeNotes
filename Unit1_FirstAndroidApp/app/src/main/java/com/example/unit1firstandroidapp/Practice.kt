package com.example.unit1firstandroidapp


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unit1firstandroidapp.ui.theme.Unit1FirstAndroidAppTheme


@Composable
fun Practice(modifier: Modifier = Modifier){
    Column(modifier = modifier){
        Image(
            painter = painterResource(R.drawable.bg_compose_background),
            contentDescription = "compose image",
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = stringResource(R.string.jetpack_compose_tutorial),
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = stringResource(R.string.compose_description),
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Justify
        )
        Text(
            text = stringResource(R.string.tuttorial_description),
            fontSize = 16.sp,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Justify
        )
    }
}


@Composable
fun Practice2(modifier: Modifier){
    Column(
        modifier= modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_task_completed),
            contentDescription = "task completed image"
        )
        Text(
            text = stringResource(R.string.all_tasks_completed),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
        )
        Text(
            text = stringResource(R.string.nice_work),
            fontSize = 16.sp
        )
    }
}

@Composable
fun Practice3(modifier: Modifier){
    Column(modifier = modifier) {

        Row(
            modifier = Modifier.weight(1f)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(Color(0xFFEADDFF)),
            ){
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.text_composable),
                        fontWeight = FontWeight.Bold,
                        modifier= Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.displays_text_and_follows_the_recommended_material_design_guidelines),
                        textAlign = TextAlign.Justify,
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(Color(0xFFD0BCFF)),
            ){
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.image_composable),
                        fontWeight = FontWeight.Bold,
                        modifier= Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.creates_a_composable_that_lays_out_and_draws_a_given_painter_class_object),
                        textAlign = TextAlign.Justify,
                    )
                }
            }

        }

        Row(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(Color(0xFFB69DF8)),
            ){
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.row_composable),
                        fontWeight = FontWeight.Bold,
                        modifier= Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.a_layout_composable_that_places_its_children_in_a_horizontal_sequence),
                        textAlign = TextAlign.Justify,
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(Color(0xFFF6EDFF)),
            ){
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.column_composable),
                        fontWeight = FontWeight.Bold,
                        modifier= Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.a_layout_composable_that_places_its_children_in_a_vertical_sequence),
                        textAlign = TextAlign.Justify,
                    )
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun PracticePreview() {
    Unit1FirstAndroidAppTheme {
        Practice3(
            modifier = Modifier.fillMaxSize()
        )

    }
}