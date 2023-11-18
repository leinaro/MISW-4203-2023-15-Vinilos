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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.misw.vinilos.VinilosEvent.Idle
import com.misw.vinilos.VinilosEvent.NavigateBack
import com.misw.vinilos.VinilosEvent.NavigateTo
import com.misw.vinilos.VinilosEvent.ShowError
import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.ui.VinilosInfoDialog
import com.misw.vinilos.ui.VinilosNavigationBar
import com.misw.vinilos.ui.VinilosTopAppBar
import com.misw.vinilos.ui.album.AlbumCreate
import com.misw.vinilos.ui.album.AlbumDetail
import com.misw.vinilos.ui.album.AlbumsList
import com.misw.vinilos.ui.collector.CollectorDetailScreen
import com.misw.vinilos.ui.collector.CollectorListScreen
import com.misw.vinilos.ui.musician.MusicianDetail
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
            val event by viewModel.event.collectAsState()

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
                            event = event,
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
    event: VinilosEvent = VinilosEvent.Idle,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("albums", "artists", "collectors")
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isInfoDialogVisible by remember { mutableStateOf(false) }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    fun showSnackBar(message: String){
        try {
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    visuals = SnackbarVisualsWithError(
                        message = message,
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

    LaunchedEffect(key1 = event){
        when(event){
            is NavigateBack -> {
                navController.navigateUp()
            }
            is ShowError -> {
                showSnackBar(event.message)
            }
            is VinilosEvent.ShowSuccess -> {
                showSnackBar(event.message)
            }
            is Idle -> Unit
            is NavigateTo -> {
                navController.navigate(event.route)
            }
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
                navController = navController,
                onInfoActionClick = {
                    isInfoDialogVisible = true
                }
            )
        },
        snackbarHost = {
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
                composable(Routes.Albums.path) {
                    AlbumsList(
                        albums = state.albums,
                    )
                }
                composable(Routes.AlbumsCreate.path){
                    AlbumCreate()
                }
                composable(Routes.Artists.path) {
                    MusicianListScreen(musicianList = state.musicians, navController = navController)
                }
                composable(Routes.Collectors.path) {
                    CollectorListScreen(
                        collectorList = state.collectors
                    )
                }
                composable(
                    route = Routes.CollectorDetail.path,
                    arguments = listOf(
                        navArgument("collectorId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val collectorId = backStackEntry.arguments?.getInt("collectorId")
                    CollectorDetailScreen(
                        collectorId = collectorId
                    )
                }
                composable(Routes.AlbumDetail.path) { backStackEntry ->
                    val albumId = backStackEntry.arguments?.getString("albumId")?.toIntOrNull()
                    AlbumDetail(albumId = albumId)
                }
                composable(Routes.ArtistDetail.path) { backStackEntry ->
                    val musicianId = backStackEntry.arguments?.getString("musicianId")?.toIntOrNull()
                    MusicianDetail(musicianId = musicianId)
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
            state = VinilosViewState(),
        )
    }
}