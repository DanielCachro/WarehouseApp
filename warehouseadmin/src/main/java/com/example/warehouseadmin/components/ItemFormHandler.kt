package com.example.warehouseadmin.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.warehouseadmin.Item
import com.example.warehouseadmin.R
import com.example.warehouseadmin.getItemFromFirebaseBySku
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ItemFormHandler(
    initialSku: String = "",
    initialName: String = "",
    initialWeight: String = "",
    initialLocation: String = "",
    onSubmit: (Item) -> Unit
) {
    val accentColor = colorResource(id = R.color.orange)

    var sku by remember { mutableStateOf(TextFieldValue(initialSku)) }
    var name by remember { mutableStateOf(TextFieldValue(initialName)) }
    var weight by remember { mutableStateOf(TextFieldValue(initialWeight)) }
    var location by remember { mutableStateOf(TextFieldValue(initialLocation)) }

    var isLoading by remember { mutableStateOf(false) }
    var skuEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(initialSku) {
        if (initialSku.isNotEmpty()) {
            isLoading = true
            getItemFromFirebaseBySku(initialSku, onSuccess = { item ->
                sku = TextFieldValue(item.sku)
                name = TextFieldValue(item.name)
                weight = TextFieldValue(item.weight)
                location = TextFieldValue(item.location)
                isLoading = false
            }, onFailure = { exception ->
                isLoading = false
            })

            skuEnabled = false
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
            text = if (initialSku.isEmpty()) "You're currently adding an item to the database."
            else "You're editing an existing item.",
            style = TextStyle(
                color = accentColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Please fill in the details accurately and click the \"Save\" button to proceed.",
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
                label = "SKU",
                enabled = skuEnabled
            )
            CustomOutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name"
            )
            CustomOutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = "Weight"
            )
            CustomOutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = "Location"
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(onClick = {
                val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
                val item = Item(
                    sku = sku.text,
                    name = name.text,
                    weight = weight.text,
                    location = location.text,
                    updateDate = currentDate
                )
                onSubmit(item)
            }, text = "Save")
        }
    }
}

