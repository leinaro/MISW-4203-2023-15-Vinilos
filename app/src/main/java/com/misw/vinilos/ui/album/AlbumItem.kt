package com.misw.vinilos.ui.album

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.misw.vinilos.R.drawable
import com.misw.vinilos.data.model.Album

@Composable
fun AlbumItem(
    album: Album,
    onClick: (Album) -> Unit = {},
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(album.cover)
            .error(drawable.baseline_broken_image_24)
            .size(Size.ORIGINAL)
            .build(),
    )

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable(
                onClick = {
                    onClick(album)
                }
            ),

    ) {
        Image(
            painter = painter,
            contentDescription = album.name,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .aspectRatio(1f),
            contentScale = ContentScale.Inside,
        )

        Text(
            text = "${album.name} - ${album.genre}",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 16.dp)
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
    )
}
