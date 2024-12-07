package com.example.datastoreexample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.datastoreexample.R
import com.example.datastoreexample.data.local.LocalDessertReleaseData

@Composable
fun DessertReleaseApp(
    dessertReleaseViewModel: DessertReleaseViewModel = hiltViewModel()
) {
    DessertReleaseScreen(
        uiState = dessertReleaseViewModel.uiState.collectAsState().value,
        selectLayout = dessertReleaseViewModel::selectLayout
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DessertReleaseScreen(
    uiState: DessertReleaseUiState,
    selectLayout: (Boolean) -> Unit
) {
    val isLinearLayout = uiState.isLinearLayout
    val isLoading = uiState.isLoading
    if (isLoading){
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Loading..")
        }
    }else{
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.top_bar_name)) },
                    actions = {
                        IconButton(
                            onClick = {
                                selectLayout(!isLinearLayout)
                            }
                        ) {
                            Icon(
                                painter = painterResource(uiState.toggleIcon),
                                contentDescription = stringResource(uiState.toggleContentDescription),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.inversePrimary
                    )
                )
            }
        ) { innerPadding ->
            val modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.padding_medium),
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                )
            if (isLinearLayout) {
                DessertReleaseLinearLayout(
                    modifier = modifier.fillMaxWidth(),
                    contentPadding = innerPadding
                )
            } else {
                DessertReleaseGridLayout(
                    modifier = modifier,
                    contentPadding = innerPadding,
                )
            }
        }
    }
}
@Composable
fun DessertReleaseLinearLayout(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
    ) {
        items(
            items = LocalDessertReleaseData.dessertReleases,
            key = { dessert -> dessert }
        ) { dessert ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = dessert,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DessertReleaseGridLayout(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(150.dp),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        items(
            items = LocalDessertReleaseData.dessertReleases,
            key = { dessert -> dessert }
        ) { dessert ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.height(110.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = dessert,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(dimensionResource(R.dimen.padding_small))
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
