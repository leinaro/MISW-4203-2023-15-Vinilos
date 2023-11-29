import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import com.misw.vinilos.VinilosViewModel
import coil.size.Size

@Composable
fun MusicianAddAlbum(musicianId: Int?) {
    val composeView = LocalView.current
    val viewModel: VinilosViewModel = composeView.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel(it)
    } ?: hiltViewModel()

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

    }
}

@Composable
@Preview
fun MusicianAddAlbum() {
    MusicianAddAlbum(null)
}