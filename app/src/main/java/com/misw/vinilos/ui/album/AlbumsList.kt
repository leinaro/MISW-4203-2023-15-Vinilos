package com.misw.vinilos.ui.album

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.misw.vinilos.R.string
import com.misw.vinilos.VinilosEvent
import com.misw.vinilos.VinilosViewModel
import com.misw.vinilos.data.model.Album

@Composable
fun AlbumsList(
    albums: List<Album>,
) {
    val composeView = LocalView.current
    val viewModel = composeView.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel<VinilosViewModel>(it)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(24.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            Text(
                text = stringResource(string.albums),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        items(items = albums) { album ->
            AlbumItem(
                album = album,
                onClick = {
                    viewModel?.setEvent(VinilosEvent.NavigateTo("album/${album.id}"))
                }
            )
        }
    }
}

@Preview(showBackground = true)
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
    AlbumsList(albums = mockAlbums)
}
