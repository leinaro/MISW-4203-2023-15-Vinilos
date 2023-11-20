package com.misw.vinilos.ui.album

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.misw.vinilos.R
import com.misw.vinilos.VinilosViewModel
import com.misw.vinilos.data.model.Album
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AlbumDetail(albumId: Int?) {
    val composeView = LocalView.current
    val viewModel: VinilosViewModel = composeView.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel(it)
    } ?: hiltViewModel()

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = null) {
        if (albumId != null) {
            viewModel.getAlbum(albumId = albumId)
        }
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(state.album?.cover)
            .error(R.drawable.baseline_broken_image_24)
            .size(Size.ORIGINAL)
            .build(),
    )

    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val formattedDate = try {
        state.album?.releaseDate?.let { stringDate ->
            originalFormat.parse(stringDate)?.let { date ->
                targetFormat.format(date)
            }
        }
    } catch (e:Exception){
        state.album?.releaseDate
    }.orEmpty()


    Column (
        modifier = Modifier
            .testTag("AlbumDetail")
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painter,
            contentDescription = state.album?.name.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .aspectRatio(1f),
            contentScale = ContentScale.Inside,
        )

        Text(
            text = "Album: ${state.album?.name}",
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Genero: ${state.album?.genre}",
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
            text = state.album?.description ?: "",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 10.dp, start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview
fun AlbumDetailPreview() {
    AlbumDetail(
        albumId = 1
    )
}