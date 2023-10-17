package com.misw.vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.misw.vinilos.ui.theme.VinilosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: VinilosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val state by viewModel.state.collectAsState()

            VinilosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        state = state
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: VinilosViewState,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        snackbarHost = {

        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = "albums") {
            composable("albums") {
                Text(
                    text = "Albums ${state.albums.toString()}",
                    modifier = modifier.padding(paddingValues)
                )
            }
            composable("collections") {
                Text(
                    text = "Collections!",
                    modifier = modifier
                )
            }
            /*...*/
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VinilosTheme {
        MainScreen(VinilosViewState())
    }
}