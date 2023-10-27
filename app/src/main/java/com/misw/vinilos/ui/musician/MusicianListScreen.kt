package com.misw.vinilos.ui.musician

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.misw.vinilos.data.model.Musician

@Composable
fun  MusicianListScreen(musicianList : List<Musician>){
    LazyVerticalGrid(columns = GridCells.Fixed(2),modifier = Modifier.padding(24.dp)) {
        items(items = musicianList){ musician ->
            musicianItemView(musician)
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MusicianListScreenPreview (){
    MusicianListScreen(musicianList = listOf(Musician("1",
        "Rubén Blades Bellido de Luna",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Ruben_Blades_by_Gage_Skidmore.jpg/800px-Ruben_Blades_by_Gage_Skidmore.jpg",
        "Es un cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en la ciudad de Nueva York.",
        "1948-07-16T00:00:00-05:00"
    ),Musician("2",
        "shakira",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Ruben_Blades_by_Gage_Skidmore.jpg/800px-Ruben_Blades_by_Gage_Skidmore.jpg",
        "kajshdñlkjahskldjhaklñjs",
        "1948-07-16T00:00:00-05:00"
    )))
}