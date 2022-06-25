package com.mahmoudrh.catsapi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mahmoudrh.catsapi.ui.theme.CatsApiTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatsApiTheme {
                val viewModel: CatsViewModel = viewModel()
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = viewModel.isRefreshing.value),
                    onRefresh = { viewModel.updateCats() }) {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(viewModel.listOfResults) {
                            Log.e("Mah ", "onCreate: item")
                            CatImage(
                                url = it.url,
                                modifier = Modifier
                                    .fillParentMaxHeight(0.45f)
                                    .fillParentMaxWidth(), viewModel = viewModel
                            )
                        }
                        item {
                            Button(
                                onClick = { viewModel.updateCats() },
                                enabled = !viewModel.isRefreshing.value,
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(50.dp)
                                    .clip(
                                        CircleShape
                                    )
                                    .placeholder(
                                        visible = viewModel.isRefreshing.value,
                                        color = MaterialTheme.colorScheme.secondary,
                                        highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                                        shape = CircleShape
                                    )
                            ){
                                Text(modifier = Modifier.padding(end = 12.dp),text = "Refresh")
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = "refresh button")
                        }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CatImage(url: String, modifier: Modifier, viewModel: CatsViewModel) {
    var isLoading by remember { mutableStateOf(false)}
    val boxShape = RoundedCornerShape(topStart = 45.dp, bottomEnd = 45.dp)
    Box(
        modifier = modifier
            .padding(horizontal = 25.dp, vertical = 20.dp)
            .clip(boxShape)
            .placeholder(
                visible = isLoading,
                shape = boxShape,
                color = Color.LightGray,
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.White,
                )
            )
    ) {
        Image(
            modifier = modifier,
            painter = rememberAsyncImagePainter(model = url, onLoading = {
                          isLoading = true
            }, onSuccess = {
                          isLoading = false
            }),
            contentDescription = "cat image",
            contentScale = ContentScale.Crop,
        )
    }
}