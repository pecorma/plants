package com.pecorma.plants.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(200.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colors.onSurface.copy(alpha = 0.63f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(0.42f),
            color = Color.White,
            strokeWidth = 5.dp
        )
    }
}

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    currentLocation: LatLng,
    cameraZoom: Float = 14.5f,
    properties: MapProperties = MapProperties(maxZoomPreference = 17.0f, minZoomPreference = 13.0f, mapType = MapType.NORMAL),
    uiSettings: MapUiSettings = MapUiSettings(),
    onMapLoad: () -> Unit,
    content: @Composable () -> Unit
) {
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, cameraZoom)
    }
    GoogleMap(
        modifier = modifier,
        properties = properties,
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        onMapLoaded = onMapLoad
    ) {
        content()
    }
}
