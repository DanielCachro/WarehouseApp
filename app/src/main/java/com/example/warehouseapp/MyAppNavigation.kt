package com.example.warehouseapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.startingScreen, builder = {
        composable(Routes.startingScreen) {
            StartingScreen(navController)
        }
        composable(Routes.findItemScreen) {
            FindItemScreen(navController)
        }
        composable(Routes.itemListScreen) {
            ItemListScreen(navController)
        }
    })
}