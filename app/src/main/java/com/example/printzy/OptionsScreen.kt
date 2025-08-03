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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.printzy.ui.theme.PrintzyTheme

@Composable
fun OptionsScreen(navController: NavController, viewModel: OrderViewModel) {
    val jobType = viewModel.order.value.jobType

    var selectedCover by remember { mutableStateOf("Paperback") }
    var selectedPrintOption by remember { mutableStateOf("2 sided") }
    var selectedColor by remember { mutableStateOf("B/W") }
    var selectedBookSize by remember { mutableStateOf("6 x 9") }
    var selectedOrientation by remember { mutableStateOf("Portrait") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Printzy", fontSize = 32.sp, color = Color.Black)
        Spacer(Modifier.height(24.dp))

        if (jobType == "Book") {
            OptionGroup(
                title = "Cover",
                options = listOf("Paperback", "Hardcover"),
                selectedOption = selectedCover,
                onOptionSelected = { selectedCover = it }
            )
        }

        OptionGroup(
            title = "Print Option",
            options = listOf("1 sided", "2 sided"),
            selectedOption = selectedPrintOption,
            onOptionSelected = { selectedPrintOption = it }
        )

        OptionGroup(
            title = "Color",
            options = listOf("Color", "B/W"),
            selectedOption = selectedColor,
            onOptionSelected = { selectedColor = it }
        )

        if (jobType == "Book") {
            OptionGroup(
                title = "Book Size",
                options = listOf("5 x 8", "6 x 9", "A4"),
                selectedOption = selectedBookSize,
                onOptionSelected = { selectedBookSize = it }
            )
        }

        OptionGroup(
            title = "Orientation",
            options = listOf("Portrait", "Landscape"),
            selectedOption = selectedOrientation,
            onOptionSelected = { selectedOrientation = it }
        )

        Spacer(Modifier.height(32.dp))


        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                viewModel.setOptions(
                    cover = if (jobType == "Book") selectedCover else "",
                    printOption = selectedPrintOption,
                    color = selectedColor,
                    bookSize = if (jobType == "Book") selectedBookSize else "",
                    orientation = selectedOrientation
                )

                navController.navigate("upload")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF))
        ) {
            Text("Next", color = Color.White, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun OptionGroup(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium, color = Color.Black)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            options.forEach { option ->
                SelectableButton(
                    text = option,
                    isSelected = option == selectedOption,
                    onClick = { onOptionSelected(option) }
                )
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun SelectableButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val buttonBlue = Color(0xFF007BFF)
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) buttonBlue else Color.White,
            contentColor = if (isSelected) Color.White else buttonBlue
        ),
        border = if (!isSelected) BorderStroke(1.dp, buttonBlue) else null
    ) {
        Text(text)
    }
}

// A preview for our new screen
@Preview(showBackground = true)
@Composable
fun OptionsScreenPreview() {
    PrintzyTheme {
        val navController = rememberNavController()
        val viewModel = OrderViewModel()
        viewModel.setJobType("Book")
        OptionsScreen(navController = navController, viewModel = viewModel)
    }
}