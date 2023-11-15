package com.misw.vinilos.ui.musician

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.misw.vinilos.VinilosViewModel
import com.misw.vinilos.data.model.Musician
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MusicianDetail(musicianId: Int?) {
    val composeView = LocalView.current
    val viewModel = composeView.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel<VinilosViewModel>(it)
    } ?: hiltViewModel<VinilosViewModel>()

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = null) {
        if (musicianId != null) {
            viewModel.getMusician(musicianId = musicianId)
        }
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(state.musician?.image)
            .size(Size.ORIGINAL)
            .build()
    )

    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val formattedDate = if (state.musician?.birthDate != null) originalFormat.parse(state.musician?.birthDate)
        ?.let { targetFormat.format(it) } else "Unknown Date"

    Column (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painter,
            contentDescription = state.musician?.name,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(10.dp),

            contentScale = ContentScale.Fit,

            )

        Text(
            text = "Artista: ${state.musician?.name}",
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Fecha de nacimiento: $formattedDate",
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        )

        Text(
            text = state.musician?.description ?: "",
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
fun MusicianDetailPreview() {
    MusicianDetail(null)
}
