package com.misw.vinilos.ui

import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.misw.vinilos.Album
import com.misw.vinilos.ui.components.AlbumItem
import java.time.Instant
import java.time.LocalDateTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumCreate() {
    var imagePath by remember { mutableStateOf("") }
    var releaseDate by remember { mutableStateOf(Calendar.getInstance()) }
    Card(modifier = Modifier.background(Color.Transparent)) {
        Text(
            text = "Crear Album",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 10.dp, end = 10.dp, top = 10.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = imagePath,
                onValueChange = { },
                label = { Text("Path de la Imagen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Nombre del Álbum") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        DatePicker(state = datePickerState, modifier = Modifier.padding(16.dp))


        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Género") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
fun AlbumCreatePreview() {
    AlbumCreate()
}
