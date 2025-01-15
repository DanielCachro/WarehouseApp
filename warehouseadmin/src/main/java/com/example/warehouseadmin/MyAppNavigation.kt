package com.example.warehouseadmin
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
        composable(Routes.addItemScreen) {
            AddItemScreen(navController)
        }
        composable(Routes.itemListScreen + "/{mode}") {
            val mode = it.arguments?.getString("mode")
           ItemListScreen(navController, mode?: "edit")
        }
        composable(Routes.updateItemScreen + "/{sku}") {
            val sku = it.arguments?.getString("sku")
            UpdateItemScreen(navController, sku ?: "0")
        }
    })
}