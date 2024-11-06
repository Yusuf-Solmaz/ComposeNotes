package com.example.unit3_materialdesign.practice_1

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit3_materialdesign.data.Hero
import com.example.unit3_materialdesign.ui.theme.Unit3_MaterialDesignTheme
import com.example.unit3_materialdesign.R
import com.example.unit3_materialdesign.data.HeroesRepository.heroes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch


@Composable
fun SuperHeroesApp() {
    // Kahraman listesini dinamik olarak güncelleyebilmek için mutable state list kullanıyoruz.
    val heroesList = remember { mutableStateListOf(*heroes.toTypedArray()) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                SuperHeroTopBar()
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Scaffold'un iç kenar boşluklarına dikkat ediyoruz.
                ) {
                    // Yeni Kahraman Ekle butonu
                    Button(
                        onClick = {
                            heroesList.add(
                                Hero(
                                    nameRes = R.string.description2,
                                    descriptionRes = R.string.description1,
                                    imageRes = R.drawable.faye
                                )
                            )
                            coroutineScope.launch {
                                listState.animateScrollToItem(heroesList.size - 1)
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp) // Buton çevresine kenar boşlukları ekliyoruz.
                            .fillMaxWidth() // Butonu geniş yaparak görünürlüğünü artırıyoruz.
                    ) {
                        Text("Yeni Kahraman Ekle")
                    }



                    // Kahramanlar listesini görüntüleme
                    HeroesList(heroes = heroesList, contentPadding = PaddingValues(16.dp),listState = listState)
                }
            }
        )
    }
}

@Composable
fun LaunchedEffect(x0: Int, content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}

@Composable
fun CustomButton( heroesList: MutableList<Hero>){
    Button(
        onClick = {
            // Yeni bir kahraman ekleyin. Bu örnekte sabit bir kahraman ekliyoruz.
            heroesList.add(
                Hero(
                    nameRes = R.string.hero2,
                    descriptionRes = R.string.description4,
                    imageRes = R.drawable.android_superhero3
                )
            )
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Yeni Kahraman Ekle")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHeroTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.super_heroes),
                style = MaterialTheme.typography.displayLarge,
            )
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HeroesList(
    heroes: MutableList<Hero>,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        state = listState,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        itemsIndexed(heroes) { index, hero ->
            // Yeni eklenen kahramanın animasyonunu kontrol etmek için bir state kullanıyoruz
            val visibleState = remember { MutableTransitionState(false).apply { targetState = true } }

            AnimatedVisibility(
                visibleState = visibleState,
                enter = slideInVertically(
                    animationSpec = spring(
                        stiffness = StiffnessVeryLow,
                        dampingRatio = DampingRatioLowBouncy
                    ),
                    initialOffsetY = { it * (index + 1) } // Her öğe için sıralı kaydırma animasyonu
                ) + fadeIn(), // Fade-in animasyonu ekledik
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp) // Her öğe için padding ekliyoruz
            ) {
                SuperheroCard(
                    hero = hero,
                    modifier = Modifier.animateEnterExit(
                        enter = slideInVertically(
                            animationSpec = spring(
                                stiffness = StiffnessVeryLow,
                                dampingRatio = DampingRatioLowBouncy
                            ),
                            initialOffsetY = { it * (index + 1) }
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun SuperheroCard(modifier: Modifier = Modifier, hero: Hero) {
    Card(modifier = modifier.padding(8.dp) , colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primary
    ),elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(72.dp)
                .fillMaxWidth()
        ) {
            SuperHeroInfo(hero, modifier = Modifier.weight(1f))
            SuperheroImage(hero)
        }

    }
}

@Composable
fun SuperHeroInfo(hero: Hero, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(end = dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(hero.nameRes),
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(Modifier.size(16.dp))
        Text(
            text = stringResource(hero.descriptionRes),
            style = MaterialTheme.typography.bodyLarge

        )
    }
}

@Composable
fun SuperheroImage(hero: Hero, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .height(72.dp)
            .width(72.dp),
        painter = painterResource(hero.imageRes),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Unit3_MaterialDesignTheme (darkTheme = false){
        SuperHeroesApp()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultDarkPreview() {
    Unit3_MaterialDesignTheme (darkTheme = true){
        SuperHeroesApp()
    }
}