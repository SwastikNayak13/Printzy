package com.example.printzy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.printzy.ui.theme.PrintzyTheme

@Composable
fun GettingStartedScreen(navController: NavController, viewModel: OrderViewModel){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "Printzy",
                fontSize = 48.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Select the print job",
                fontSize = 18.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                JobTypeButton(
                    text = "Book",
                    icon = Icons.Default.Book,
                    onClick = {
                        viewModel.setJobType("Book")
                        navController.navigate("options")
                    }
                )
                Spacer(modifier = Modifier.width(24.dp))
                JobTypeButton(
                    text = "Document",
                    icon = Icons.Default.Description,
                    onClick = {
                        viewModel.setJobType("Document")
                        navController.navigate("options")
                    }
                )
            }
        }
    }
}

@Composable
fun JobTypeButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
){
    OutlinedCard(
        onClick = onClick,
        modifier = Modifier.size(width = 140.dp, height = 120.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(40.dp),
                tint = Color.Black
            )
            Spacer(Modifier.height(8.dp))
            Text(text = text, color = Color.Black)
        }
    }
}

// This allows you to preview your screen without running the app.
@Preview(showBackground = true)
@Composable
fun GettingStartedScreenPreview() {
    PrintzyTheme {
        // We provide dummy versions of the NavController and ViewModel for the preview.
        // This allows the UI to render without needing the full app context.
        GettingStartedScreen(
            navController = rememberNavController(),
            viewModel = OrderViewModel()
        )
    }
}