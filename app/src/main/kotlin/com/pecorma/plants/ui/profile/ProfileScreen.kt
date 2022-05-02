package com.pecorma.plants.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    Surface(modifier = Modifier.fillMaxSize(),) {
        val tabIndex = remember { mutableStateOf(0) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .background(color = MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.46f)
                    .clip(shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(color = MaterialTheme.colorScheme.background),
            ) {
                ProfileTabLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.1f)
                        .align(Alignment.BottomCenter),
                    tabIndex
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (tabIndex.value) {
                    0 -> Text(text = "Layout 1")
                    1 -> Text(text = "Layout 2")
                    2 -> Text(text = "Layout 3")
                }
            }
        }
    }
}

@Composable
fun ProfileTabLayout(modifier: Modifier = Modifier, tabIndex: MutableState<Int>) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = tabIndex.value,
        containerColor = Color.Transparent,
        indicator = {},
        divider = {}
    ) {
        ProfileTab(selected = tabIndex.value == 0, text = "ABOUT", onClick = { tabIndex.value = 0 })
        ProfileTab(selected = tabIndex.value == 1, text = "REVIEWS", onClick = { tabIndex.value = 1 })
        ProfileTab(selected = tabIndex.value == 2, text = "BADGES", onClick = { tabIndex.value = 2 })
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
        Tab(
            modifier = modifier,
            selected = selected,
            onClick = onClick,
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = if (selected) FontWeight.Black else FontWeight.Light
            )
        }
    }
}

object ClearRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = Color.Transparent

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        draggedAlpha = 0.0f,
        focusedAlpha = 0.0f,
        hoveredAlpha = 0.0f,
        pressedAlpha = 0.0f,
    )
}

@Preview
@Composable
fun Preview() {
    ProfileScreen()
}
