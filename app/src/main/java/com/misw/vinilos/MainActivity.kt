package com.misw.vinilos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.misw.vinilos.ui.album.AlbumCreate
import com.misw.vinilos.ui.VinilosInfoDialog
import com.misw.vinilos.ui.VinilosNavigationBar
import com.misw.vinilos.ui.VinilosTopAppBar
import com.misw.vinilos.ui.album.AlbumsList
import com.misw.vinilos.ui.musician.MusicianListScreen
import com.misw.vinilos.ui.theme.VinilosTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class SnackbarVisualsWithError(
    override val message: String,
    val isError: Boolean
) : SnackbarVisuals {
    override val actionLabel: String
        get() = if (isError) "Error" else "OK"
    override val withDismissAction: Boolean
        get() = false
    override val duration: SnackbarDuration
        get() = SnackbarDuration.Short

}

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

    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("albums", "artists", "collections")
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isInfoDialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = state.error){
        if (state.error == null) return@LaunchedEffect
        try {
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    SnackbarVisualsWithError(
                        state.error,
                        isError = true,
                    )
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        //Do Something
                    }
                    SnackbarResult.Dismissed -> {
                        //Do Something
                        Log.e("iarl", "SnackbarResult.Dismissed")
                    }
                }
            }
        } finally {
            //onDismissSnackBarState()
        }
    }

    Scaffold(
        topBar = {
            VinilosTopAppBar(
                onInfoActionClick = {
                    isInfoDialogVisible = true
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier,
                ) {
                    Text(data.visuals.message)
                }
            }
        },
        bottomBar = {
            VinilosNavigationBar(
                onItemSelected = { selected ->
                    selectedItem = selected
                    navController.navigate(items[selected])
                },
                selected = selectedItem
            )
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "albums",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("albums") {
                AlbumsList(albums = state.albums, {navController.navigate("albums-create")})
            }
            composable("albums-create"){
                AlbumCreate()
            }
            composable("artists") {
                MusicianListScreen(musicianList = state.musicians)
            }
            composable("collections") {
                Text(
                    text = "Collections!",
                    modifier = modifier.padding(paddingValues)
                )
            }
        }
    }

    if (isInfoDialogVisible) {
        VinilosInfoDialog(
            onDismissRequest = {
                isInfoDialogVisible = false
            })
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    VinilosTheme(darkTheme = true) {
        MainScreen(VinilosViewState())
    }
}