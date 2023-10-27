package com.misw.vinilos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.misw.vinilos.Album
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AlbumsList(albums: List<Album>,  navigateToMainAccountCreation: () -> Unit) {
    Card(modifier = Modifier.background(Color.Transparent)) {
        Text(
            text = "Albumes",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 10.dp, end = 10.dp, top = 10.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(16.dp)
        ) {
            items(albums.size) { index ->
                AlbumItem(album = albums[index])
            }
        }
        FloatingActionButton(
            onClick = {
                navigateToMainAccountCreation()
            },
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp) // Tamaño del FloatingActionButton
                .offset(
                    x = 16.dp, // Ajusta la posición en el eje X
                    y = 16.dp // Ajusta la posición en el eje Y
                ),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar álbum",
            )
        }

    }

}

@Preview
@Composable
fun AlbumsListPreview() {
    val mockAlbums = listOf(
        Album(
            name = "Album name",
            cover = "https://f4.bcbits.com/img/a3726590002_65",
            releaseDate = "2021-01-01",
            description = "Album description",
            genre = "Album genre",
            recordLabel = "Album record label",
        ),
        Album(
            name = "Album name 2",
            cover = "https://f4.bcbits.com/img/a3726590002_65",
            releaseDate = "2021-01-01",
            description = "Album description 2",
            genre = "Album genre 2",
            recordLabel = "Album record label 2",
        )
        // Add more mock albums for preview
    )
    AlbumsList(albums = mockAlbums, {})
}
