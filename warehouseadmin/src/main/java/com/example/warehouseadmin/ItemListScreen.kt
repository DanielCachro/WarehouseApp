package com.example.warehouseadmin

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
import com.example.warehouseadmin.components.ConfirmDeleteModal

@Composable
fun ItemListScreen(navController: NavController, mode: String) {
    var items by remember { mutableStateOf<List<Item>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        getItemsFromFirebase(
            onSuccess = { fetchedItems ->
                items = fetchedItems
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
                        ItemRow(
                            mode = mode,
                            item = item,
                            onEdit = {
                                navController.navigate(Routes.updateItemScreen + "/${item.sku}")
                            },
                            onDelete = {
                                deleteItemFromFirebase(item.sku,
                                    onSuccess = {
                                        Log.d("ConfirmDeleteModal", "Product successfully deleted")
                                    },
                                    onFailure = { exception ->
                                        Log.e("ConfirmDeleteModal", "Error deleting product: ${exception.message}")
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemRow(
    mode: String,
    item: Item,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val accentColor = colorResource(id = R.color.orange)
    val whiteColor = colorResource(id = R.color.white)
    val cornerRadius = 12.dp
    var showModal by remember { mutableStateOf(false) }

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

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (mode == "edit") {
                        onEdit()
                    } else if (mode == "delete") {
                        showModal = true
                    }
                },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
            ) {
                if (mode == "edit") {
                    Text(text = "Edit", color = whiteColor)
                } else if (mode == "delete") {
                    Text(text = "Delete", color = whiteColor)
                }
            }
        }
    }

    if (showModal) {
        ConfirmDeleteModal(
            itemName = item.name,
            onConfirm = {
                onDelete()
                showModal = false
            },
            onDismiss = {
                showModal = false
            }
        )
    }
}
