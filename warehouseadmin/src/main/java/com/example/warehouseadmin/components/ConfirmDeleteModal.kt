package com.example.warehouseadmin.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.warehouseadmin.R

@Composable
fun ConfirmDeleteModal(
    itemName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val whiteColor = colorResource(id = R.color.white)
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Confirm Deletion")
        },
        text = {
            Text(text = "Are you sure you want to delete \"$itemName\"? This action cannot be undone.")
        },
        confirmButton = {
            Button(
                onClick = { onConfirm() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Delete", color = whiteColor)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        }
    )
}
