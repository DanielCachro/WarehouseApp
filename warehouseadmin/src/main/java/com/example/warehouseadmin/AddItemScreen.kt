package com.example.warehouseadmin

import addToFirebase
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.warehouseadmin.components.CustomOutlinedTextField
import com.example.warehouseadmin.components.PrimaryButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddItemScreen(navController: NavController) {
    val accentColor = colorResource(id = R.color.orange)
    val whiteColor = colorResource(id = R.color.white)

    var sku by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var weight by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "You're currently adding an item to the database.",
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

        CustomOutlinedTextField(
            value = sku,
            onValueChange = { sku = it },
            label = "SKU",
        )
        CustomOutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = "Name",
        )
        CustomOutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = "Weight",
        )
        CustomOutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = "Location",
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
        }, text = "Save")
    }
}