package com.misw.vinilos.ui.musician


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.misw.vinilos.data.model.Musician

@Composable
fun musicianItemView(musician: Musician, navController: NavController) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(musician.image)
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )

    Column (modifier = Modifier.padding(8.dp).fillMaxWidth()
        .clickable(onClick = { navController.navigate("musician/${musician.id}") })
        ) {
        Image(
            painter = painter,
            contentDescription = "foto artista",
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Fit
        )
        Text(
            text = musician.name,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp, start = 4.dp, end = 4.dp).fillMaxWidth()
        )

    }



}

@Preview(showBackground = true)
@Composable
fun musicianItemViewPreview(){
    musicianItemView(Musician("1",
        "Rubén Blades Bellido de Luna",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Ruben_Blades_by_Gage_Skidmore.jpg/800px-Ruben_Blades_by_Gage_Skidmore.jpg",
        "Es un cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en la ciudad de Nueva York.",
        "1948-07-16T00:00:00-05:00"
        ),navController = rememberNavController()
    )



}