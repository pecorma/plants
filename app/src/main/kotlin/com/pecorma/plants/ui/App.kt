package com.pecorma.plants.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun App(
    appState: JetcasterAppState = rememberJetcasterAppState()
) {
    if (appState.isOnline) {
        NavHost(
            navController = appState.navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {}
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
