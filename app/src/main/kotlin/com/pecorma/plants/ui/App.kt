package com.pecorma.plants.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pecorma.plants.ui.map.Map
import com.pecorma.plants.ui.map.MapScreen
import com.pecorma.plants.ui.profile.ProfileScreen
import com.pecorma.plants.ui.watchers.WatchersScreen

@Composable
fun App(
    appState: AppState = rememberJetcasterAppState()
) {
    if (appState.isOnline) {
        NavHost(
            navController = appState.navController,
            startDestination = Screen.Profile.route
        ) {
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.Map.route) {
                MapScreen()
            }
            composable(Screen.Watchers.route) {
                WatchersScreen()
            }
        }
    } else {
        OfflineDialog { appState.refreshOnline() }
    }
}

@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Connection error") },
        text = { Text(text = "connection error") },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text("retry")
            }
        }
    )
}
