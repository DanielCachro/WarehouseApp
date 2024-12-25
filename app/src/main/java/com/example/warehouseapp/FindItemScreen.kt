package com.example.warehouseapp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.warehouseapp.components.CustomOutlinedTextField
import com.example.warehouseapp.components.PrimaryButton

@Composable
fun FindItemScreen(
    navController: NavController,
) {
    val accentColor = colorResource(id = R.color.orange)

    var sku by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var item by remember { mutableStateOf<Item?>(null) }

    fun searchItem() {
        if (sku.text.isNotEmpty()) {
            isLoading = true
            errorMessage = ""
            item = null

            getItemFromFirebaseBySku(sku.text, onSuccess = { foundItem ->
                isLoading = false
                item = foundItem
            }, onFailure = { exception ->
                isLoading = false
                errorMessage = "Item not found."
            })
        } else {
            errorMessage = "Please enter an SKU to search."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Search for an Item by SKU",
            style = TextStyle(
                color = accentColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter the SKU below to view item details.",
            style = TextStyle(
                color = accentColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            CustomOutlinedTextField(
                value = sku,
                onValueChange = { sku = it },
                label = "SKU"
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(onClick = { searchItem() }, text = "Search")
        }

        item?.let {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, colorResource(id = R.color.orange), shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "SKU: ${it.sku}", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                    Text(text = "Name: ${it.name}", style = TextStyle(fontSize = 16.sp))
                    Text(text = "Weight: ${it.weight}", style = TextStyle(fontSize = 16.sp))
                    Text(text = "Location: ${it.location}", style = TextStyle(fontSize = 16.sp))
                    Text(text = "Last Updated: ${it.updateDate}", style = TextStyle(fontSize = 16.sp))
                }
            }
        }
    }
}
