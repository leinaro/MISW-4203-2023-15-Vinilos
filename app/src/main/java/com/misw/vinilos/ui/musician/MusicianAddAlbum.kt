import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
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
import com.misw.vinilos.R
import com.misw.vinilos.ui.album.errorMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicianAddAlbum(musicianId: Int?) {
    val composeView = LocalView.current
    val context = LocalContext.current
    val viewModel: VinilosViewModel = composeView.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel(it)
    } ?: hiltViewModel()

    val state by viewModel.state.collectAsState()
    var albumLabel by remember { mutableStateOf("") }
    var albumLabelError by remember { mutableStateOf("") }
    var selectedAlbumId by remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(key1 = null) {
        if (musicianId != null) {
            viewModel.getAlbumsByMusicianId(musicianId)
            viewModel.getMusician(musicianId = musicianId)
        }
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(state.musician?.image)
            .size(Size.ORIGINAL)
            .build()
    )

    fun validateRecordLabel(record: String):Boolean {
        albumLabelError = if (record.isEmpty()) {
            context.getString(R.string.empty_album)
        } else {
            ""
        }
        return albumLabelError.isEmpty()
    }

    LazyColumn (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painter,
                    contentDescription = state.musician?.name,
                    modifier = Modifier
                        .width(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .padding(10.dp),

                    contentScale = ContentScale.Fit,

                    )
            }
        }
        item{
        Text(
            text = "Artista: ${state.musician?.name}",
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        )}
        item{
        Text(
            text = "Agregar album",
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 28.dp, end = 28.dp)
                .fillMaxWidth()
        )}

        item {
            val options = state.albums
                .filter { it.musicianId == null }
                .map { album -> album.name to album.id }
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = albumLabel,
                    isError = albumLabel.isNotEmpty(),
                    onValueChange = { albumLabel = it },
                    label = { androidx.compose.material3.Text(stringResource(R.string.album_name)) },
                    modifier = Modifier
                        .testTag("record-field")
                        .fillMaxWidth()
                        .menuAnchor(),
                    supportingText = if (albumLabelError.isNotEmpty()) {
                        errorMessage(albumLabelError)
                    } else {
                        null
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                albumLabel = selectionOption.first
                                selectedAlbumId=   selectionOption.second
                                expanded = false
                            },
                            text = {
                                androidx.compose.material3.Text(text = selectionOption.first)
                            }
                        )
                    }
                }
            }
        }

        item {
            Button(
                onClick = {
                    if (listOf(
                            validateRecordLabel(albumLabel),
                        ).contains(false)){
                        return@Button
                    }
                    selectedAlbumId?.let { albumId ->
                        viewModel?.addAlbumToMusician(albumId, musicianId)
                    }

                },
                modifier = Modifier
                    .testTag("AlbumCreate")
                    .fillMaxWidth()
            ) {
                androidx.compose.material3.Text(
                    text = stringResource(R.string.add_new),
                )
            }
        }

    }
}

@Composable
@Preview
fun MusicianAddAlbum() {
    MusicianAddAlbum(null)
}
