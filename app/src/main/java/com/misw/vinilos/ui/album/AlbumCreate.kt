package com.misw.vinilos.ui.album

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.ZoneOffset
import java.util.Calendar
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.painter.ColorPainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumCreate() {
    var imagePath by remember { mutableStateOf("") }
    var albumName by remember { mutableStateOf("") }
    var generoName by remember { mutableStateOf("") }
    var canciones  = remember {
        mutableStateListOf("")
    }
    val defaultImage = ColorPainter(color = Color.Gray)
    val context = LocalContext.current
    var selectedDateText by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Crear Álbum",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = defaultImage,
                    contentDescription = "Imagen por defecto",
                    modifier = Modifier.size(20.dp)
                )
                TextField(
                    value = imagePath,
                    onValueChange = { imagePath = it },
                    label = { Text("Path de la Imagen") },
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                )
            }
        }


    item {
        OutlinedTextField(
            value = albumName,
            onValueChange = { albumName = it },
            label = { Text("Nombre del Álbum") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }

    item {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (selectedDateText.isNotEmpty()) {
                    "$selectedDateText"
                } else {
                    "Por favor, selecciona una fecha"
                }
            )

            Button(
                modifier = Modifier.size(30.dp),
                onClick = {
                    datePicker.show()
                }
            ) {
                Text(text = "Fecha de lanzamiento")
            }
        }
    }

    item {
        OutlinedTextField(
            value = generoName,
            onValueChange = { generoName = it },
            label = { Text("Género") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }
    items(canciones.size) { index ->
        OutlinedTextField(
            value = canciones[index],
            onValueChange = { canciones[index] = it },
            label = { Text("Canción") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }


item {
    Button(
        onClick = {
            canciones.add("")
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("+")
    }
}

item {
    Button(
        onClick = {
            // Lógica para crear el álbum
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Crear")
    }
}

    }}

@Preview
@Composable
fun AlbumCreatePreview() {
    AlbumCreate()
}
