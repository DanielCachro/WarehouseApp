package com.example.warehouseapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.warehouseapp.R

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    text: String,
) {
    val accentColor = colorResource(id = R.color.orange)
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = accentColor)
    ) {
        Text(text = text, color = Color.White)
    }
}
