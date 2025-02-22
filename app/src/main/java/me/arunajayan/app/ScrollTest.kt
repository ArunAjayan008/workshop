package me.arunajayan.app

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollableLazyColumnWithDynamicInnerText() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    var innerItemOffset by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(top = 30.dp))

        // Button to scroll to a specific inner text
        Button(
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(
                        index = 15,
                        scrollOffset = innerItemOffset
                    )
                }
            }
        ) {
            Text("Scroll to Inner Text 10")
        }
        val bringIntoViewRequester = remember { BringIntoViewRequester() }

        LaunchedEffect(true) {
            coroutineScope.launch {
                bringIntoViewRequester.bringIntoView(rect = Rect(left = 0F, right = 0F, bottom = 1000F, top = 0F))
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(200) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (index == 15) {
                        // Inner Column with 20 dynamic height Text items
                        Column {
                            Box(
                                modifier = Modifier
                                    .size(100.dp) // Small size
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Small Box",
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Box 2: Medium box with Button
                            Box(
                                modifier = Modifier
                                    .size(200.dp, 100.dp) // Medium size
                                    .background(Color.Blue),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = { /* Handle button click */ },
                                ) {
                                    Text(
                                        text = "Click Me",
                                        fontSize = 14.sp,
                                        color = Color.White
                                    )
                                }
                            }

                            Button(
                                onClick = { /* Handle button click */ }
                            ) {
                                Text(
                                    text = "Click Me",
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            // Box 3: Large box with Text
                            Box(
                                modifier = Modifier
                                    .size(300.dp, 150.dp) // Large size
                                    .background(Color.Green),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Large Box",
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            }
                            Button(
                                onClick = { /* Handle button click */ },
                                modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester)
                            ) {
                                Text(
                                    text = "Click Me",
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                    } else {
                        Text(text = "Box $index")
                    }
                }
            }
        }
    }
}
