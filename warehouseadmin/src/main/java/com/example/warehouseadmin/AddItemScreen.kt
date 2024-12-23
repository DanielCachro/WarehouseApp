package com.example.warehouseadmin

import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavController

@Composable
fun AddItemScreen(navController: NavController) {
    ItemFormHandler(
        onSubmit = { item ->
            addToFirebase(
                item = item,
                onSuccess = {
                    Log.d("AddItemScreen", "Item successfully added to Firebase: $item")
                    navController.popBackStack()
                },
                onFailure = { exception ->
                    Log.e("AddItemScreen", "Failed to add item to Firebase", exception)
                }
            )
        }
    )
}