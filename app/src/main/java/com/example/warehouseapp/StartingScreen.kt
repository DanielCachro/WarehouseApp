package com.example.warehouseapp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun StartingScreen(navController: NavController) {
    val accentColor = colorResource(id = R.color.orange)
    val whiteColor = colorResource(id = R.color.white)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to the Warehouse App!",
            style = TextStyle(
                color = accentColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Click one of the buttons below to get started.",
            style = TextStyle(
                color = accentColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    navController.navigate(Routes.findItemScreen)
                },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .border(2.dp, accentColor, shape = RoundedCornerShape(50)),
                colors = ButtonDefaults.buttonColors(containerColor = whiteColor)
            ) {
                Text(text = "Find Item", color = accentColor)
            }

            Button(
                onClick = {
                    navController.navigate(Routes.itemListScreen)
                },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .border(2.dp, accentColor, shape = RoundedCornerShape(50)),
                colors = ButtonDefaults.buttonColors(containerColor = whiteColor)
            ) {
                Text(text = "View Items", color = accentColor)
            }
        }
    }
}
