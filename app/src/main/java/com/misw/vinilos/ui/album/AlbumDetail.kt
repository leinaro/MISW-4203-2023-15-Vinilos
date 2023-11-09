package com.misw.vinilos.ui.album

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.misw.vinilos.data.model.Album
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AlbumDetail(album: Album?) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(album?.cover)
            .size(Size.ORIGINAL)
            .build()
    )

    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val formattedDate = if (album?.releaseDate != null) originalFormat.parse(album.releaseDate)
        ?.let { targetFormat.format(it) } else "Unknown Date"


    Column (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painter,
            contentDescription = album?.name,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(10.dp),

            contentScale = ContentScale.Fit,

            )

        Text(
            text = "Album: ${album?.name}",
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Genero: ${album?.genre}",
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Año: $formattedDate",
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        )

        Text(
            text = album?.description ?: "",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 10.dp, start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        )
    }

//    Box(modifier = Modifier.fillMaxSize()) {
//        Column {
//            Image(
//                painter = painter,
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Fit
//            )
//        }
//        Column(
//            modifier = Modifier
//                .align(Alignment.BottomStart)
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = album?.name ?: "",
//                style = typography.headlineSmall,
//                modifier = Modifier
//                    .padding(bottom = 8.dp)
//                    .align(Alignment.CenterHorizontally)
//            )
//            Text(
//                text = "Genero: ${album?.genre}",
//                style = typography.headlineSmall,
//                modifier = Modifier
//                    .padding(bottom = 4.dp)
//                    .align(Alignment.CenterHorizontally)
//            )
//            Text(
//                text = "Año: ${album?.releaseDate}",
//                style = typography.headlineSmall,
//                modifier = Modifier
//                    .padding(bottom = 16.dp)
//                    .align(Alignment.CenterHorizontally)
//            )
//            Text(
//                text = album?.description ?: "", // Este debe ser el contenido real del álbum
//                style = typography.bodyMedium,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//        }
//    }


}

@Composable
@Preview
fun AlbumDetailPreview() {
    AlbumDetail(
        album = Album(
            name = "Album name",
            cover = "https://f4.bcbits.com/img/a3726590002_65",
            releaseDate = "2021-01-01",
            description = "Album description",
            genre = "Album genre",
            recordLabel = "Album record label",
        )
    )
}