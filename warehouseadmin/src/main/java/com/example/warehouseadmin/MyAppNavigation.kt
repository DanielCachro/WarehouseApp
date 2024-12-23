package com.example.warehouseadmin
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.startingScreen, builder = {
        composable(Routes.screenA) {
            ScreenA(navController)
        }
        composable(Routes.screenB + "/{name}") {
            val name = it.arguments?.getString("name")
            ScreenB(name ?: "No name")
        }
        composable(Routes.startingScreen) {
            StartingScreen(navController)
        }
        composable(Routes.addItemScreen) {
            AddItemScreen(navController)
        }
        composable(Routes.itemListScreen) {
           ItemListScreen(navController)
        }
        composable(Routes.updateItemScreen + "/{sku}") {
            val sku = it.arguments?.getString("sku")
            UpdateItemScreen(navController, sku ?: "0")
        }
    })
}
