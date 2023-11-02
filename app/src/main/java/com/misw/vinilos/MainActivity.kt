package com.misw.vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.misw.vinilos.ui.VinilosInfoDialog
import com.misw.vinilos.ui.VinilosNavigationBar
import com.misw.vinilos.ui.VinilosTopAppBar
import com.misw.vinilos.ui.album.AlbumCreate
import com.misw.vinilos.ui.album.AlbumsList
import com.misw.vinilos.ui.musician.MusicianListScreen
import com.misw.vinilos.ui.theme.VinilosTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class SnackbarVisualsWithError(
    override val message: String,
    val isError: Boolean,
) : SnackbarVisuals {
    override val actionLabel: String
        get() = if (isError) "OK" else "OK"
    override val withDismissAction: Boolean
        get() = false
    override val duration: SnackbarDuration
        get() = SnackbarDuration.Long

}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: VinilosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val state by viewModel.state.collectAsState()
            val isRefreshing by viewModel.isRefreshing.collectAsState()
            val isInternetAvailable by viewModel.isInternetAvailable.collectAsState()

            var offlineBannerVisible by rememberSaveable {
                mutableStateOf(true)
            }

            VinilosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()){
                        if (isInternetAvailable.not() && offlineBannerVisible){
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(4.dp)
                                    .fillMaxWidth(),
                            ) {
                                Text(
                                    modifier = Modifier.padding(4.dp),
                                    text="Estás en modo ermitaño digital. Sin internet.",
                                    color = MaterialTheme.colorScheme.surfaceTint,
                                )
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(4.dp)
                                        .clickable {
                                            offlineBannerVisible = false
                                        },
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "cerrar",
                                )
                            }
                        }
                        MainScreen(
                            state = state,
                            isRefreshing = isRefreshing,
                            onRefresh = {
                                viewModel.getAllInformation()
                            },
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: VinilosViewState,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
) {
    val navController = rememberNavController()

    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("albums", "artists", "collections")
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isInfoDialogVisible by remember { mutableStateOf(false) }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    LaunchedEffect(key1 = state.error){
        if (state.error == null) return@LaunchedEffect
        try {
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    visuals = SnackbarVisualsWithError(
                        message = state.error,
                        isError = true,
                    ),
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        //Do Something
                    }
                    SnackbarResult.Dismissed -> {
                        //Do Something
                    }
                }
            }
        } finally {
            //onDismissSnackBarState()
        }
    }

    Scaffold(
        floatingActionButton = {
            when (currentBackStackEntry?.destination?.route){
                "albums" -> FloatingActionButton(
                    onClick = {
                        navController.navigate("albums-create")
                    },
                ) {
                    Icon(Icons.Filled.Add, "Agregar nuevo.")
                }
            }
        },
        topBar = {
            VinilosTopAppBar(
                onInfoActionClick = {
                    isInfoDialogVisible = true
                }
            )
        },
        snackbarHost = {
            //
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
        Box(modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
        ){
            NavHost(
                navController = navController,
                startDestination = "albums",
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                composable("albums") {
                    AlbumsList(albums = state.albums)
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

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = isRefreshing,
                state = pullRefreshState,
            )

            SnackbarHost(snackbarHostState) { data ->
                Snackbar {
                    Text(data.visuals.message)
                }
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
    VinilosTheme(dynamicColor = false, darkTheme = true) {
        MainScreen(
            state = VinilosViewState()
        )
    }
}