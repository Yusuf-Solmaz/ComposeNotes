package com.example.pageprogressexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pageprogressexample.ui.theme.PageProgressExampleTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.StrokeCap
import androidx.lifecycle.viewmodel.compose.viewModel

interface Displayable {
    val displayName: String
}

enum class Category(override val displayName: String) : Displayable {
    SPOR("Spor"),
    TEKNOLOJI("Teknoloji"),
    UZAY("Uzay")
}

enum class AgeGroup(override val displayName: String) : Displayable {
    AGE_10_15("10-15"),
    AGE_16_25("16-25"),
    AGE_26_35("26-35"),
    AGE_35_PLUS("35+")
}

enum class Gender(override val displayName: String) : Displayable {
    KADIN("Kadın"),
    ERKEK("Erkek")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PageProgressExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProgressIndicatorAppWithEnums(innerPadding)
                }
            }
        }
    }
}

@Composable
fun ProgressIndicatorAppWithEnums(innerPaddingValues: PaddingValues, viewModel: ProgressViewModel = viewModel()) {
    val navController = rememberNavController()
    val totalPages = 3
    val currentPage = rememberSaveable { mutableStateOf(1) }

    val pages = listOf(
        Triple("Bir Kategori Seçin", Category.values(), 0),
        Triple("Yaş Grubunuzu Seçin", AgeGroup.values(), 1),
        Triple("Cinsiyetinizi Seçin", Gender.values(), 2)
    )

    val animatedProgress by animateFloatAsState(
        targetValue = currentPage.value.toFloat() / totalPages,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )

    Scaffold(
        topBar = {
            LinearProgressIndicator(
                progress = {animatedProgress},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeCap = StrokeCap.Butt
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "page1",
            modifier = Modifier.padding(it)
        ) {
            pages.forEachIndexed { index, pageContent ->
                composable("page${index + 1}") {
                    DynamicPageWithEnumSelection(
                        viewModel = viewModel,
                        pageIndex = index,
                        title = pageContent.first,
                        options = pageContent.second,
                        onNext = {
                            if (index + 1 < totalPages) {
                                currentPage.value++
                                navController.navigate("page${index + 2}")
                            } else {
                                navController.navigate("finalPage"){
                                    popUpTo("page1") { inclusive = true }
                                }
                            }
                        },
                        onBack = {
                            if (index > 0) {
                                currentPage.value--
                                navController.popBackStack()
                            }
                        },
                        showBackButton = index > 0
                    )
                }
            }

            // Son Sayfa
            composable("finalPage") {
                FinalPage(
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun <T> DynamicPageWithEnumSelection(
    viewModel: ProgressViewModel,
    pageIndex: Int,
    title: String,
    options: Array<T>,
    onNext: () -> Unit,
    onBack: () -> Unit,
    showBackButton: Boolean
) where T : Enum<T>, T : Displayable {
    val uiState = viewModel.uiState.value
    val selectedOption = when (pageIndex) {
        0 -> uiState.categorySelection
        1 -> uiState.ageGroupSelection
        2 -> uiState.genderSelection
        else -> null
    } as? T

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        options.forEach { option ->
            val isSelected = selectedOption == option
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        when (pageIndex) {
                            0 -> viewModel.updateSelection(category = option as? Category)
                            1 -> viewModel.updateSelection(ageGroup = option as? AgeGroup)
                            2 -> viewModel.updateSelection(gender = option as? Gender)
                        }
                        onNext()
                    },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = option.displayName,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        if (showBackButton) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Back")
            }
        }
    }
}

@Composable
fun FinalPage(
    viewModel: ProgressViewModel
) {
    val uiState = viewModel.uiState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Seçimleriniz",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Seçimleri Listeleme
        uiState.categorySelection?.let {
            Text(
                text = "Kategori: ${it.displayName}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        uiState.ageGroupSelection?.let {
            Text(
                text = "Yaş Grubu: ${it.displayName}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        uiState.genderSelection?.let {
            Text(
                text = "Cinsiyet: ${it.displayName}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

