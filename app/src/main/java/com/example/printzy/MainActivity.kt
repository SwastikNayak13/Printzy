package com.example.printzy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.printzy.ui.theme.PrintzyTheme

class MainActivity : ComponentActivity() {
    private val orderViewModel by viewModels<OrderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrintzyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PrintzyApp(orderViewModel)
                }
            }
        }
    }
}

@Composable
fun PrintzyApp(viewModel: OrderViewModel) {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            GettingStartedScreen(navController = navController, viewModel = viewModel)
        }
        composable("options") {
            OptionsScreen(navController = navController, viewModel = viewModel)
        }
        composable("upload") {
            UploadScreen(navController = navController, viewModel = viewModel)
        }
        composable("summary") {
            SummaryScreen(navController = navController, viewModel = viewModel)
        }
        composable("confirmation") {
            ConfirmationScreen(navController = navController)
        }
    }
}