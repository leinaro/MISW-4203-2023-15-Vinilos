package com.misw.vinilos.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun VinilosInfoDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "Tears of the devs",
                modifier = Modifier

                    .padding(top = 20.dp)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,

                )
            Text(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                text = "Andres Soler, Dario Rios, Emmanuel Rodriguez, Ines Rojas",
                textAlign = TextAlign.Center,

                )

        }
    }
}

@Composable
@Preview
fun VinilosInfoDialogPreview() {
    VinilosInfoDialog(
        onDismissRequest = {},
    )
}