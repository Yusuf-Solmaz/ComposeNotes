/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marsphotos.R
import com.example.marsphotos.data.network.mars_photos.model.MarsPhoto
import com.example.marsphotos.ui.screens.state.MarsUIState
import com.example.marsphotos.ui.theme.MarsPhotosTheme

@Composable
fun HomeScreen(
    marsUiState: MarsUIState,
    loadMore: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    retryAction :() -> Unit
) {
    when(marsUiState){
        is MarsUIState.Loading -> {
            LoadingScreen(modifier = modifier.fillMaxSize())
        }
        is MarsUIState.Success -> {
            ResultScreen(
                marsUiState.photos, isLoading = isLoading,loadMore=loadMore,modifier = modifier.padding(contentPadding)
            )
        }
        is MarsUIState.Error -> {
            ErrorScreen( retryAction = retryAction,errorMessage = marsUiState.error, modifier = modifier.fillMaxSize())
        }
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(photos: List<MarsPhoto>,  isLoading: Boolean, loadMore: () -> Unit,modifier: Modifier = Modifier) {

    Column(modifier = modifier.fillMaxSize()) {
        MarsPhotoGrid(photos,isLoading,loadMore)
    }
}

@Composable
fun MarsPhotoGrid(
    photos: List<MarsPhoto>,
    isLoading: Boolean,
    loadMore: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)){

    /**
    Bu kodda kullanılan key parametresi, Compose'un öğeleri yeniden sıralarken veya yeniden çizim
    yaparken doğru durumu (state) korumasına yardımcı olur. Özellikle, LazyVerticalGrid gibi listelerde
    öğeler dinamik olarak eklendiğinde, silindiğinde veya sıralandığında, Compose hangi öğenin nerede
    olduğunu doğru bir şekilde takip etmek için key değerini kullanır.
     */

    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding) {
        items(items = photos, key = { photo -> photo.id }) { photo ->

            MarsImage(
                photo, modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )
        }

        // Listenin sonuna ulaşıldığında daha fazla veri yükle
        item {
            if (isLoading) {
                // Loading göstergesi
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            } else {
                // Scroll sonuna ulaşınca yeni veri yükle
                LaunchedEffect(Unit) {
                    loadMore()
                }
            }
        }
    }
}

@Composable
fun MarsImage(photoUrl: MarsPhoto,modifier: Modifier= Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUrl.image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            error = painterResource(R.drawable.ic_connection_error),
            contentDescription = stringResource(R.string.mars_photo),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ErrorScreen(retryAction :()-> Unit, errorMessage: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = errorMessage, modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}



@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    MarsPhotosTheme {

    }
}
