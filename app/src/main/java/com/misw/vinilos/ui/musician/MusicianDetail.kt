package com.misw.vinilos.ui.musician

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.misw.vinilos.VinilosViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MusicianDetail(musicianId: Int?, navController: NavController) {
    val composeView = LocalView.current
    val viewModel: VinilosViewModel = composeView.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel(it)
    } ?: hiltViewModel()

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = null) {
        if (musicianId != null) {
            viewModel.getAlbumsByMusicianId(musicianId)
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
        /*Text(
            text = "Albumes",
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2),modifier = Modifier.padding(24.dp)) {
            item(span = { GridItemSpan(currentLineSpan = 2) }){
                Text(text = stringResource(R.string.artists), fontWeight= FontWeight.Bold, color= MaterialTheme.colorScheme.primary, fontSize = 24.sp, modifier=Modifier.fillMaxWidth())
            }
            items(items = state.albumsByMusician){ album ->
                AlbumItem(album, {})
            }
        }*/

        FloatingActionButton(
            onClick = {navController.navigate("musician/add-album/${musicianId}") },
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Go to another screen")
        }
    }
}

@Composable
@Preview
fun MusicianDetailPreview() {
    MusicianDetail(1, rememberNavController())
}
