package com.misw.vinilos.ui.collector

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.misw.vinilos.VinilosEvent
import com.misw.vinilos.VinilosViewModel
import com.misw.vinilos.data.model.Collector

@Composable
fun CollectorDetailScreen(
    collectorId: Int? = null,
    ) {
    val composeView = LocalView.current
    val viewModel = composeView.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel<VinilosViewModel>(it)
    }?: hiltViewModel<VinilosViewModel>()

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = null) {
        if (collectorId != null) {
            viewModel.getCollector(collectorId = collectorId)
        }
    }

    /*if (collector == null) {
        //viewModel?.setEvent(VinilosEvent.ShowError("No encontramos el coleccionista."))
        viewModel?.setEvent(VinilosEvent.NavigateBack)
        return
    }*/
    Column(
        modifier = Modifier,
        ) {
        Text(
            text = "${state.collector?.name}",
        )

        Text(
            text = "${state.collector?.email}",
        )
        Text(
            text = "${state.collector?.telephone}",
        )
    }
}

@Composable
@Preview
fun CollectorDetailPreview() {
    CollectorDetailScreen()
}