package com.example.printzy // Use your package name

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.printzy.ui.theme.PrintzyTheme

@Composable
fun SummaryScreen(navController: NavController, viewModel: OrderViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Printzy", fontSize = 32.sp, color = Color.Black)
        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OrderSummary(order = viewModel.order.value, modifier = Modifier.weight(1f))

            ShippingAddressForm(modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("confirmation") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF))
        ) {
            Text("Place Order", color = Color.White, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun OrderSummary(order: PrintOrder, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Order Summary", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            SummaryRow("Print Job Type:", order.jobType)

            val options = buildOptionsSummary(order).replace("\n", ", ")
            SummaryRow("Print Options:", options)

            // Mock (fake) data as shown in the presentation
            SummaryRow("Pages in Document:", "200...")
            SummaryRow("Total Printing Cost:", "₹400.00")
            SummaryRow("Shipping:", "₹50.00")

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                Text("Grand Total:", fontWeight = FontWeight.Bold)
                Spacer(Modifier.weight(1f))
                Text("₹450.00", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ShippingAddressForm(modifier: Modifier = Modifier) {
    var fullName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var zip by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Shipping Address", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Address") }, modifier = Modifier.fillMaxWidth(), maxLines = 3)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = state, onValueChange = { state = it }, label = { Text("State") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = zip, onValueChange = { zip = it }, label = { Text("ZIP Code") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth())
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


@Preview(showBackground = true, widthDp = 800)
@Composable
fun SummaryScreenPreview() {
    PrintzyTheme {
        val navController = rememberNavController()
        val viewModel = OrderViewModel()
        viewModel.setJobType("Book")
        viewModel.setOptions("Paperback", "2 sided", "Black & White", "6x9", "Portrait")
        SummaryScreen(navController = navController, viewModel = viewModel)
    }
}