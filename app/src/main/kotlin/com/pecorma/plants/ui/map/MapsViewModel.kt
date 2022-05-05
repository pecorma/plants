package com.pecorma.plants.ui.map

import android.annotation.SuppressLint
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.pecorma.plants.base.BaseViewModel
import com.pecorma.plants.base.Reducer
import com.pecorma.plants.base.UiEvent
import com.pecorma.plants.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class MapsViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : BaseViewModel<MapState, MapEvent>() {
    private val reducer = mapReducer

    override val state: Flow<MapState> = reducer.state

    val timeMachine = reducer.timeCapsule

    private val cancellationToken = object : CancellationToken() {
        override fun isCancellationRequested(): Boolean = false

        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken = this
    }

    fun getCurrentLocation() = viewModelScope.launch {
        fusedLocationProviderClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationToken
        ).addOnSuccessListener {
            if (it != null) {
                sendEvent(MapEvent.OnLocationChange(LatLng(it.latitude, it.longitude)))
            }
        }
    }

    fun sendEvent(event: MapEvent) {
        reducer.sendEvent(event)
    }

    companion object {
        private val mapReducer = object : Reducer<MapState, MapEvent>(MapState.initial()) {
            override fun reduce(oldState: MapState, event: MapEvent) {
                when (event) {
                    is MapEvent.OnMapLoadChange ->
                        setState(oldState.copy(isMapLoading = event.isLoading))

                    is MapEvent.OnLocationChange ->
                        setState(oldState.copy(
                            isLocationLoading = event.currentLocation == null,
                            currentLocation = event.currentLocation ?: LatLng(40.71, -74.00)
                        ))
                }
            }
        }
    }
}

@Immutable
data class MapState(
    val isMapLoading: Boolean,
    val isLocationLoading: Boolean,
    val currentLocation: LatLng
) : UiState {
    companion object {
        fun initial() = MapState(
            isMapLoading = true,
            isLocationLoading = true,
            currentLocation = LatLng(40.71, 74.00)
        )
    }
}

sealed class MapEvent : UiEvent {
    data class OnMapLoadChange(val isLoading: Boolean = true) : MapEvent()
    data class OnLocationChange(val currentLocation: LatLng? = null) : MapEvent()
}
