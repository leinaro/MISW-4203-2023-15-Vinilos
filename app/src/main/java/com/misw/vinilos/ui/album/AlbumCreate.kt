package com.misw.vinilos.ui.album

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.misw.vinilos.VinilosEvent
import com.misw.vinilos.VinilosViewModel
import java.util.Calendar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.testTag
import com.misw.vinilos.data.model.Album
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumCreate(
    onCreateAlbumClick: (Album) -> Unit = {},
) {
    val composeView = LocalView.current
    val viewModel = composeView.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel<VinilosViewModel>(it)
    }
    var imagePath by remember { mutableStateOf("") }
    var albumName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var generoName by remember { mutableStateOf("") }
    var recordLabel by remember { mutableStateOf("") }

    val defaultImage = ColorPainter(color = Color.Gray)
    val context = LocalContext.current
    var selectedDateText by rememberSaveable { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    var year by rememberSaveable {
        mutableIntStateOf(calendar[Calendar.YEAR])
    }
    var month by rememberSaveable {
        mutableIntStateOf(calendar[Calendar.MONTH])
    }
    var dayOfMonth by rememberSaveable {
        mutableIntStateOf(calendar[Calendar.DAY_OF_MONTH])
    }
    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )
    LazyColumn(
        modifier = Modifier
            .testTag("CreateAlbumContainer")
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
                    modifier = Modifier.size(85.dp)
                )
                OutlinedTextField(
                    value = imagePath,
                    onValueChange = { imagePath = it },
                    label = { Text("Url de la Imagen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )
            }
        }

        item {
            OutlinedTextField(
                value = albumName,
                onValueChange = { albumName = it },
                label = { Text("Nombre del Álbum") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }


        item {
            OutlinedTextField(
                value = selectedDateText,
                onValueChange = { selectedDateText = it },
                label = { Text("Fecha de lanzamiento") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Filled.DateRange,
                        contentDescription = "Seleccionar fecha.",
                        modifier = Modifier.clickable {
                            datePicker.show()
                        },
                    )
                },
                readOnly = true,
            )
        }

        item {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }

        item {
            OutlinedTextField(
                value = generoName,
                onValueChange = { generoName = it },
                label = { Text("Género") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }
        item {
            OutlinedTextField(
                value = recordLabel,
                onValueChange = { recordLabel = it },
                label = { Text("Discográfica") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }

        item {
            Button(
                onClick = {
                    /*val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
                    val date = LocalDate.parse(selectedDateText, formatter)

                    // define a formatter for your desired output
                    val formatterUTC = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSX",
                        Locale.ENGLISH)

                   // val date = LocalDate.of(2020, 5, 16)
                    val formatted = date.format(formatterUTC)
                    println(formatted)
                    Log.e("iarl", formatted)*/
                    try{
                        val inputFormat = SimpleDateFormat("dd/MM/yyyy")
                        val outputFormat = SimpleDateFormat("yyyy-MM-dd")
                        val date = inputFormat.parse(selectedDateText)
                        val finalDate= outputFormat.format(date)
                        Log.i("finalDate",  finalDate)
                        val album = Album(
                            name = albumName,
                            cover = imagePath,
                            releaseDate = finalDate,
                            description = description,
                            genre = generoName,
                            recordLabel = recordLabel
                        )
                        onCreateAlbumClick(album)
                    } catch (e: Exception){
                        Log.e("Error Album", "Error"   +  e.message)
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear")
            }
        }
    }
}

@Preview
@Composable
fun AlbumCreatePreview() {
    AlbumCreate()
}
