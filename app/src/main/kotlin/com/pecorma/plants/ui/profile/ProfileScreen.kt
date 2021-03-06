package com.pecorma.plants.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.pecorma.plants.ui.MapView
import com.pecorma.plants.ui.theme.utils.ClearRippleTheme
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen() {
    Surface(modifier = Modifier.fillMaxSize(),) {
        val tabIndex = remember { mutableStateOf(0) }
        val pagerState = rememberPagerState()
        val scrollState = rememberLazyListState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .background(color = MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.46f)
                    .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(color = MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = null,
                            contentDescription = "",
                            modifier = Modifier
                                .height(130.dp)
                                .width(130.dp)
                                .clip(CircleShape)
                                .align(CenterHorizontally)
                                .background(Color.White)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            modifier = Modifier.align(CenterHorizontally),
                            text = "Jake Pecoraro",
                            fontSize = 20.sp
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                ProfileTabLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.1f),
                    tabIndex,
                    pagerState
                )
            }
            ProfileHorizontalPager(
                modifier = Modifier.fillMaxSize(),
                pagerState = pagerState,
                scrollState = scrollState
            )
        }
    }
}

@Composable
fun ProfileHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    scrollState: LazyListState
) {
    HorizontalPager(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        count = 3,
        state = pagerState
    ) {
        when (currentPage) {
            0 -> ProfileAboutSection(scrollState)
            1 -> Text(text = "Layout 2")
            2 -> Text(text = "Layout 3")
        }
    }
}

@Composable
fun ProfileTabLayout(
    modifier: Modifier = Modifier,
    tabIndex: MutableState<Int>,
    pagerState: PagerState
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = tabIndex.value,
        backgroundColor = Color.Transparent,
        indicator = {
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, it),
                height = 0.dp
            )
        },
        divider = {}
    ) {
        val coroutineScope = rememberCoroutineScope()
        ProfileTab(
            selected = pagerState.currentPage == 0,
            text = "ABOUT",
            onClick = {
                coroutineScope.launch { pagerState.animateScrollToPage(0) }
                tabIndex.value = 0
            }
        )
        ProfileTab(
            selected = pagerState.currentPage == 1,
            text = "REVIEWS",
            onClick = {
                coroutineScope.launch { pagerState.animateScrollToPage(1) }
                tabIndex.value = 1
            }
        )
        ProfileTab(
            selected = pagerState.currentPage == 2,
            text = "BADGES",
            onClick = {
                coroutineScope.launch { pagerState.animateScrollToPage(2) }
                tabIndex.value = 2
            }
        )
    }
}

val aboutText = LoremIpsum(100).values.joinToString(" ")

@Composable
fun ProfileAboutSection(scrollState: LazyListState) {
    LazyColumn(
        modifier = Modifier
            .clipToBounds()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        state = rememberLazyListState()
    ) {
        item {
            Spacer(modifier = Modifier.height(0.dp))
        }
        item {
            ProfileAboutMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
        }
        item {
            Text(
                text = aboutText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
fun ProfileAboutMap(modifier: Modifier = Modifier) {
    MapView(
        modifier = modifier.clip(RoundedCornerShape(6.dp)),
        currentLocation = LatLng(40.71, -74.00),
        properties = MapProperties(maxZoomPreference = 14.5f, minZoomPreference = 14.5f),
        uiSettings = MapUiSettings(zoomControlsEnabled = false, zoomGesturesEnabled = false),
        onMapLoad = {}
    ) {
        Circle(
            center = LatLng(40.71, -74.00),
            radius = 125.0,
            fillColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.63f),
            strokeWidth = 0f
        )
    }
}

@Composable
fun ProfileTab(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    CompositionLocalProvider(
        LocalRippleTheme provides ClearRippleTheme
    ) {
        Tab(modifier = modifier, selected = selected, onClick = onClick) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (selected) FontWeight.Black else FontWeight.Light
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    ProfileScreen()
}
