package com.misw.vinilos.ui.musician

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.misw.vinilos.data.model.Musician
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MusicianDetail(musician: Musician) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(musician?.image)
            .size(Size.ORIGINAL)
            .build()
    )

    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val formattedDate = if (musician?.birthDate != null) originalFormat.parse(musician.birthDate)
        ?.let { targetFormat.format(it) } else "Unknown Date"

    Column (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painter,
            contentDescription = musician?.name,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(10.dp),

            contentScale = ContentScale.Fit,

            )

        Text(
            text = "Artista: ${musician?.name}",
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
            text = musician?.description ?: "",
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
    MusicianDetail(
        musician = Musician(
            id = "1",
            image = "https://static.wikia.nocookie.net/michael-jackson-espanol/images/2/28/Michael_Jackson.webp/revision/latest?cb=20220626235224&path-prefix=es",
            name = "Album name",
            birthDate = "2021-01-01",
            description = "Album description"
        )
    )
}
