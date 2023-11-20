package com.misw.vinilos.ui.album

import android.app.DatePickerDialog
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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.misw.vinilos.R.string
import com.misw.vinilos.VinilosViewModel
import com.misw.vinilos.data.model.Album
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
    var genreName by remember { mutableStateOf("") }
    var recordLabel by remember { mutableStateOf("") }

    val defaultImage = ColorPainter(color = Color.Gray)
    val context = LocalContext.current
    var selectedDateText by rememberSaveable { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    val year by rememberSaveable {
        mutableIntStateOf(calendar[Calendar.YEAR])
    }
    val month by rememberSaveable {
        mutableIntStateOf(calendar[Calendar.MONTH])
    }
    val dayOfMonth by rememberSaveable {
        mutableIntStateOf(calendar[Calendar.DAY_OF_MONTH])
    }
    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth"
        }, year, month, dayOfMonth
    )

    var urlError by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var releaseDateError by remember { mutableStateOf("") }
    var descriptionError by remember { mutableStateOf("") }
    var genreError by remember { mutableStateOf("") }
    var recordLabelError by remember { mutableStateOf("") }

    fun validateUrl(url: String):Boolean {
        urlError = if (url.isEmpty()) {
            context.getString(string.empty_url)
        } else {
            ""
        }
        return urlError.isEmpty()
    }
    fun validateName(name: String):Boolean {
        nameError = if (name.isEmpty()) {
            context.getString(string.empty_name)
        } else {
            ""
        }
        return nameError.isEmpty()
    }
    fun validateReleaseDate(date: String):Boolean {
        releaseDateError = if (date.isEmpty()) {
            context.getString(string.empty_date)
        } else {
            ""
        }
        return releaseDateError.isEmpty()
    }
    fun validateDescription(desc: String):Boolean {
        descriptionError = if (desc.isEmpty()) {
            context.getString(string.empty_desc)
        } else {
            ""
        }
        return descriptionError.isEmpty()
    }
    fun validateGenre(genre: String):Boolean {
        genreError = if (genre.isEmpty()) {
            context.getString(string.empty_genre)
        } else {
            ""
        }
        return genreError.isEmpty()
    }

    fun validateRecordLabel(record: String):Boolean {
        recordLabelError = if (record.isEmpty()) {
            context.getString(string.empty_record)
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
                text = stringResource(string.create_album),
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
                    contentDescription = stringResource(string.default_image),
                    modifier = Modifier.size(85.dp)
                )
                OutlinedTextField(
                    value = imagePath,
                    isError = urlError.isNotEmpty(),
                    onValueChange = { imagePath = it },
                    label = { Text(stringResource(string.image_url)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    supportingText = if (urlError.isNotEmpty()) {
                        errorMessage(urlError)
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
                label = { Text(stringResource(string.album_name)) },
                modifier = Modifier.fillMaxWidth(),
                supportingText = if (nameError.isNotEmpty()) {
                    errorMessage(nameError)
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
                label = { Text(stringResource(string.release_date)) },
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Filled.DateRange,
                        contentDescription = stringResource(string.select_date),
                        modifier = Modifier.clickable {
                            datePicker.show()
                        },
                    )
                },
                readOnly = true,
                supportingText = if (releaseDateError.isNotEmpty()) {
                    errorMessage(releaseDateError)
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
                label = { Text(stringResource(string.description)) },
                modifier = Modifier
                    .fillMaxWidth(),
                supportingText = if (descriptionError.isNotEmpty()) {
                    errorMessage(descriptionError)
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
                    value = genreName,
                    isError = genreError.isNotEmpty(),
                    onValueChange = { genreName = it },
                    label = { Text(stringResource(string.genre)) },
                    modifier = Modifier
                        .testTag("genre-field")
                        .fillMaxWidth()
                        .menuAnchor(),
                    supportingText = if (genreError.isNotEmpty()) {
                        errorMessage(genreError)
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
                                genreName = selectionOption
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
                    label = { Text(stringResource(string.record_label)) },
                    modifier = Modifier
                        .testTag("record-field")
                        .fillMaxWidth()
                        .menuAnchor(),
                    supportingText = if (recordLabelError.isNotEmpty()) {
                        errorMessage(recordLabelError)
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
                            validateGenre(genreName),
                            validateRecordLabel(recordLabel),
                        ).contains(false)){
                        return@Button
                    }

                    val album = Album(
                        name = albumName,
                        cover = imagePath,
                        releaseDate = selectedDateText,
                        description = description,
                        genre = genreName,
                        recordLabel = recordLabel
                    )
                    viewModel?.createAlbum(album)

                },
                modifier = Modifier
                    .testTag("AlbumCreate")
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(string.create),
                )
            }
        }
    }
}

@Preview
@Composable
fun AlbumCreatePreview() {
    AlbumCreate()
}

fun errorMessage(message: String) =  @Composable {
    Text(message)
}