package com.example.unit3_affirmationlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit3_affirmationlist.data.Topic
import com.example.unit3_affirmationlist.ui.theme.Unit3_AffirmationListTheme


/**
 aspectRatio(): bir bileşenin genişlik ve yükseklik oranını tanımlamak için kullanılır.
 */


@Composable
fun VerticalTopicGrid(items: List<Topic>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(items) { item ->
            GridTopicCard(item)
        }
    }
}

@Composable
fun GridTopicCard(topic: Topic,modifier: Modifier = Modifier){
    Card(
        modifier = modifier.size(height = 100.dp, width = 250.dp).padding(8.dp)
    ) {
        Row(
            modifier= Modifier.fillMaxSize()
        ){
            Column (
                modifier = Modifier.weight(1f).fillMaxHeight()
            ){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(topic.imageResourceId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.weight(2f).fillMaxHeight().padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(topic.stringResourceId))
                }
                Spacer(
                    modifier = Modifier.size(15.dp)
                )
                Row (
                    modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
                    horizontalArrangement = Arrangement.Start
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_topic),
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text =topic.numberOfCourses.toString()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickyHeaderExample() {
    val groupedItems = listOf(
        "Fruits" to listOf("Apple", "Banana", "Cherry"),
        "Vegetables" to listOf("Carrot", "Potato", "Broccoli"),
        "Dairy" to listOf("Milk", "Cheese", "Yogurt"),
        "Fruits" to listOf("Apple", "Banana", "Cherry"),
        "Vegetables" to listOf("Carrot", "Potato", "Broccoli"),
        "Dairy" to listOf("Milk", "Cheese", "Yogurt"),
        "Fruits" to listOf("Apple", "Banana", "Cherry"),
        "Vegetables" to listOf("Carrot", "Potato", "Broccoli"),
        "Dairy" to listOf("Milk", "Cheese", "Yogurt"),
        "Fruits" to listOf("Apple", "Banana", "Cherry"),
        "Vegetables" to listOf("Carrot", "Potato", "Broccoli"),
        "Dairy" to listOf("Milk", "Cheese", "Yogurt"),
        "Fruits" to listOf("Apple", "Banana", "Cherry"),
        "Vegetables" to listOf("Carrot", "Potato", "Broccoli"),
        "Dairy" to listOf("Milk", "Cheese", "Yogurt"),
        "Fruits" to listOf("Apple", "Banana", "Cherry"),
        "Vegetables" to listOf("Carrot", "Potato", "Broccoli"),
        "Dairy" to listOf("Milk", "Cheese", "Yogurt"),
        "Fruits" to listOf("Apple", "Banana", "Cherry"),
        "Vegetables" to listOf("Carrot", "Potato", "Broccoli"),
        "Dairy" to listOf("Milk", "Cheese", "Yogurt"),
        "Fruits" to listOf("Apple", "Banana", "Cherry"),
        "Vegetables" to listOf("Carrot", "Potato", "Broccoli"),
        "Dairy" to listOf("Milk", "Cheese", "Yogurt")
        
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        groupedItems.forEach { (header, itemsList) ->
            // Sticky header için
            stickyHeader {
                Text(
                    text = header,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            // Listedeki alt öğeler
            items(itemsList) { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopicPreview() {
    Unit3_AffirmationListTheme {
        StickyHeaderExample()
    }
}