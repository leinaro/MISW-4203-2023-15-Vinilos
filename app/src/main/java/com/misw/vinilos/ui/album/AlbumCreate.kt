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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.misw.vinilos.VinilosEvent.ShowError
import com.misw.vinilos.VinilosViewModel
import com.misw.vinilos.data.model.Album
import com.misw.vinilos.data.repository.UIError
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumCreate() {
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

    var urlError by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var releaseDateError by remember { mutableStateOf("") }
    var descriptionError by remember { mutableStateOf("") }
    var genreError by remember { mutableStateOf("") }
    var recordLabelError by remember { mutableStateOf("") }

    fun validateUrl(albumName: String):Boolean {
        urlError = if (albumName.isEmpty()) {
            "La url no puede estar vacío"
        } else {
            ""
        }
        return urlError.isEmpty()
    }
    fun validateName(albumName: String):Boolean {
        nameError = if (albumName.isEmpty()) {
            "El nombre no puede estar vacío"
        } else {
            ""
        }
        return nameError.isEmpty()
    }
    fun validateReleaseDate(albumName: String):Boolean {
        releaseDateError = if (albumName.isEmpty()) {
            "La fecha de lanzamiento no no puede estar vacío"
        } else {
            ""
        }
        return releaseDateError.isEmpty()
    }
    fun validateDescription(albumName: String):Boolean {
        descriptionError = if (albumName.isEmpty()) {
            "La descripción no puede estar vacío"
        } else {
            ""
        }
        return descriptionError.isEmpty()
    }
    fun validateGenre(albumName: String):Boolean {
        genreError = if (albumName.isEmpty()) {
            "El genero no puede estar vacío"
        } else {
            ""
        }
        return genreError.isEmpty()
    }

    fun validateRecordLabel(albumName: String):Boolean {
        recordLabelError = if (albumName.isEmpty()) {
            "La discografica no puede estar vacío"
        } else {
            ""
        }
        return recordLabelError.isEmpty()
    }

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
                    isError = urlError.isNotEmpty(),
                    onValueChange = { imagePath = it },
                    label = { Text("Url de la Imagen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    supportingText = if (urlError.isNotEmpty()) {
                        ErrorMessage(nameError)
                    } else {
                        null
                    },
                )
            }
        }

        item {
            OutlinedTextField(
                value = albumName,
                isError = nameError.isNotEmpty(),
                onValueChange = {
                    albumName = it
                    validateName(it)
                },
                label = { Text("Nombre del Álbum") },
                modifier = Modifier.fillMaxWidth(),
                supportingText = if (nameError.isNotEmpty()) {
                    ErrorMessage(nameError)
                } else {
                    null
                },
            )
        }


        item {
            OutlinedTextField(
                value = selectedDateText,
                isError = releaseDateError.isNotEmpty(),
                onValueChange = { selectedDateText = it },
                label = { Text("Fecha de lanzamiento") },
                modifier = Modifier
                    .fillMaxWidth(),
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
                supportingText = if (releaseDateError.isNotEmpty()) {
                    ErrorMessage(releaseDateError)
                } else {
                    null
                },
            )
        }

        item {
            OutlinedTextField(
                value = description,
                isError = descriptionError.isNotEmpty(),
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth(),
                supportingText = if (descriptionError.isNotEmpty()) {
                    ErrorMessage(descriptionError)
                } else {
                    null
                }
            )
        }

        item {
            val options = listOf("Classical", "Salsa", "Rock", "Folk")
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = generoName,
                    isError = genreError.isNotEmpty(),
                    onValueChange = { generoName = it },
                    label = { Text("Género") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    supportingText = if (genreError.isNotEmpty()) {
                        ErrorMessage(genreError)
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
                                generoName = selectionOption
                                expanded = false
                            },
                            text = {
                                Text(text = selectionOption)
                            }
                        )
                    }
                }
            }
        }
        item {
            val options = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = recordLabel,
                    isError = recordLabelError.isNotEmpty(),
                    onValueChange = { recordLabel = it },
                    label = { Text("Discográfica") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    supportingText = if (recordLabelError.isNotEmpty()) {
                        ErrorMessage(recordLabelError)
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
                                recordLabel = selectionOption
                                expanded = false
                            },
                            text = {
                                Text(text = selectionOption)
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
                            validateUrl(imagePath),
                            validateName(albumName),
                            validateReleaseDate(selectedDateText),
                            validateDescription(description),
                            validateGenre(generoName),
                            validateRecordLabel(recordLabel),
                        ).contains(false)){
                        return@Button
                    }

                    try{
                        val inputFormat = SimpleDateFormat("dd/MM/yyyy")
                        val outputFormat = SimpleDateFormat("yyyy-MM-dd")
                        Log.e("iarl", "selectedDateText $selectedDateText")
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
                        viewModel?.createAlbum(album)
                    } catch (e: Exception){
                        viewModel?.setEvent(
                            ShowError(UIError.UnknownError.message.orEmpty())
                        )
                        Log.e("Error Album", "Error"   +  e.message)
                        e.printStackTrace()
                    }

                },
                modifier = Modifier
                    .testTag("AlbumCreate")
                    .fillMaxWidth()
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

fun ErrorMessage(message: String) =  @Composable {
    Text(message)
}