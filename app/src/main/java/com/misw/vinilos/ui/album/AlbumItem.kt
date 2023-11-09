package com.misw.vinilos.ui.album

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.misw.vinilos.data.model.Album
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun AlbumItem(album: Album, navController: NavController) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(album.cover)
            .size(coil.size.Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate("album/${album.id}") }),

    ) {
        Image(
            painter = painter,
            contentDescription = album.name,
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Fit,
        )

        Text(
            text = "${album.name} - ${album.genre}",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 10.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview
fun AlbumItemPreview() {
    AlbumItem(
        album = Album(
            name = "Album name",
            cover = "https://f4.bcbits.com/img/a3726590002_65",
            releaseDate = "2021-01-01",
            description = "Album description",
            genre = "Album genre",
            recordLabel = "Album record label",
        ),
        navController = rememberNavController()
    )
}
