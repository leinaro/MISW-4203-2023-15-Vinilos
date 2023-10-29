package com.misw.vinilos.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.misw.vinilos.ui.theme.VinilosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VinilosTopAppBar(
    onInfoActionClick : () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Vinilos",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
            )
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
    VinilosTheme(darkTheme = true) {
        VinilosTopAppBar()
    }
}