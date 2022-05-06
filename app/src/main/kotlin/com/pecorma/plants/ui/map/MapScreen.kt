package com.pecorma.plants.ui.map

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.pecorma.plants.ui.Loading

@Composable
fun MapScreen(viewModel: MapsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState(initial = MapState.initial())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)),
        contentAlignment = Alignment.Center
    ) {
        LocationPermission {
            viewModel.getCurrentLocation()
        }
        when {
            state.isLocationLoading && state.isMapLoading -> Loading()
            else -> Map(state) { viewModel.sendEvent(MapEvent.OnMapLoadChange(false)) }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun Map(state: MapState, onMapLoad: () -> Unit) {
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.currentLocation, 14.5f)
    }
    val markerState = rememberMarkerState(position = state.currentLocation)

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = MapProperties(maxZoomPreference = 17.0f, minZoomPreference = 13.0f, mapType = MapType.NORMAL),
        cameraPositionState = cameraPositionState,
        onMapLoaded = onMapLoad
    ) {
        Marker(state = markerState)
    }
}

@Composable
fun LocationPermission(onGranted: () -> Unit) {
    val permissionState = rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)
    when (permissionState.status) {
        is PermissionStatus.Granted -> onGranted()
        is PermissionStatus.Denied -> {
            if (permissionState.status.shouldShowRationale.not()) {
                LaunchedEffect(permissionState) {
                    permissionState.launchPermissionRequest()
                }
            }
        }
    }
}
