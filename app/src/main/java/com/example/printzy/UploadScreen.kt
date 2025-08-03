package com.example.printzy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.printzy.ui.theme.PrintzyTheme

@Composable
fun UploadScreen(navController: NavController, viewModel: OrderViewModel) {
    val order = viewModel.order.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Printzy", fontSize = 32.sp, color = Color.Black)
        Spacer(Modifier.height(48.dp))
        Text(
            "Print Options",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = buildOptionsSummary(order),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.DarkGray
        )

        Spacer(Modifier.height(48.dp))

        if (order.jobType == "Book") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FileUploadBox(title = "Upload Cover PDF", modifier = Modifier.weight(1f))
                FileUploadBox(title = "Upload inner pages PDF", modifier = Modifier.weight(1f))
            }
        } else {
            FileUploadBox(title = "Upload document", modifier = Modifier.fillMaxWidth(0.7f))
        }

        Spacer(Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("summary") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF))
        ) {
            Text("Submit", color = Color.White, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun FileUploadBox(title: String, modifier: Modifier = Modifier) {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    Column(
        modifier = modifier
            .border(
                BorderStroke(2.dp, Color.LightGray),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, color = Color.DarkGray)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { /* File Picker */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Text("Choose File")
        }
    }
}


private fun buildOptionsSummary(order: PrintOrder): String {
    val builder = StringBuilder()
    if (order.jobType == "Book") {
        builder.appendLine(order.cover)
    }
    builder.appendLine(order.printOption)
    builder.appendLine(order.orientation)
    builder.appendLine(order.color)
    if (order.jobType == "Book") {
        builder.appendLine("Size: ${order.bookSize}")
    }
    return builder.toString().trim()
}


@Preview(showBackground = true)
@Composable
fun UploadScreenBookPreview() {
    PrintzyTheme {
        val navController = rememberNavController()
        val viewModel = OrderViewModel()
        viewModel.setJobType("Book")
        viewModel.setOptions("Paperback", "2 sided", "B/W", "6 x 9", "Portrait")
        UploadScreen(navController = navController, viewModel = viewModel)
    }
}