package com.pecorma.plants.ui.watchers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WatchersScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom + WindowInsetsSides.Top)),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(20) {
                WatcherCard()
            }
        }
    }
}

@Composable
fun WatcherCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 6.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
                    .background(color = Color.White),
                model = null,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "Jake Pecoraro",
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White
                )
                Text(
                    text = "The greenest thumb around!",
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(5) {
                        Box(
                            modifier = Modifier
                                .height(10.dp)
                                .width(10.dp)
                                .clip(CircleShape)
                                .background(color = Color.White)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                    item {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "5 reviews",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.White,
                            lineHeight = MaterialTheme.typography.subtitle1.fontSize
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "$15",
                    color = Color.White,
                    style = MaterialTheme.typography.h6
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview
@Composable
fun Preview() {
    WatchersScreen()
}
