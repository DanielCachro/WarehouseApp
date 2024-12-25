package com.example.warehouseapp

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController

@Composable
fun ItemListScreen(navController: NavController) {
    var items by remember { mutableStateOf<List<Item>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        getItemsFromFirebase(
            onSuccess = { fetchedItems ->
                items = fetchedItems.sortedBy { it.name }
                errorMessage = null
                isLoading = false
            },
            onFailure = { exception ->
                errorMessage = "Error: ${exception.message}"
                Log.e("ItemListScreen", "Error when fetching items", exception)
                isLoading = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 18.sp
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    items(items) { item ->
                        ItemRow(item = item)
                    }
                }
            }
        }
    }
}



@Composable
fun ItemRow(
    item: Item
) {
    val accentColor = colorResource(id = R.color.orange)
    val whiteColor = colorResource(id = R.color.white)
    val cornerRadius = 12.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(whiteColor, shape = RoundedCornerShape(cornerRadius))
            .border(BorderStroke(1.dp, accentColor), shape = RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "SKU: ${item.sku}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Name: ${item.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Weight: ${item.weight}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Location: ${item.location}", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Updated on: ${item.updateDate}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
