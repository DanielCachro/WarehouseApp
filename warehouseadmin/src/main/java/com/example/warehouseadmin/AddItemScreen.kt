package com.example.warehouseadmin

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.warehouseadmin.components.ItemFormHandler

@Composable
fun AddItemScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    ItemFormHandler(
        onSubmit = { item ->
            isLoading = true
            errorMessage = null
            addToFirebase(
                item = item,
                onSuccess = {
                    Log.d("AddItemScreen", "Item successfully added to Firebase: $item")
                    isLoading = false
                    navController.popBackStack()
                },
                onFailure = { exception ->
                    Log.e("AddItemScreen", "Failed to add item to Firebase", exception)
                    isLoading = false
                    errorMessage = "Failed to add item: ${exception.message}"
                }
            )
        }
    )

    if (isLoading) {
        LoadingIndicator()
    }
    errorMessage?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

