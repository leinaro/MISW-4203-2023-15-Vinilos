package com.misw.vinilos.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.misw.vinilos.Routes
import com.misw.vinilos.ui.theme.VinilosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VinilosTopAppBar(
    navController: NavController = rememberNavController(),
    onInfoActionClick: () -> Unit = {},
) {
    val baseRoute = listOf(Routes.Albums.path, Routes.Collectors.path, Routes.Artists.path)
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Vinilos",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
            )
        },
        navigationIcon = {
            if (currentBackStackEntry?.destination?.route !in baseRoute) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { onInfoActionClick() }) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Info",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    )
}

@Composable
@Preview
fun VinilosTopAppBarPreview() {
    VinilosTheme(dynamicColor = false, darkTheme = true) {
        VinilosTopAppBar()
    }
}