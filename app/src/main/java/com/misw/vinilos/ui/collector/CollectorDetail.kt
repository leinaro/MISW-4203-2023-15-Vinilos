package com.misw.vinilos.ui.collector

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    collector: Collector? = null,
    ) {

    val composeView = LocalView.current
    val viewModel = composeView.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel<VinilosViewModel>(it)
    }

    if (collector == null) {
        //viewModel?.setEvent(VinilosEvent.ShowError("No encontramos el coleccionista."))
        viewModel?.setEvent(VinilosEvent.NavigateBack)
        return
    }
    Column(
        modifier = Modifier,
        ) {
        Text(
            text = "${collector.name}",
        )

        Text(
            text = "${collector.email}",
        )
        Text(
            text = "${collector.telephone}",
        )
    }
}

@Composable
@Preview
fun CollectorDetailPreview() {
    CollectorDetailScreen(
        Collector(
            id = 1,
            name = "Collector name",
            telephone = "+573102178976",
            email = "j.monsalve@gmail.com"
        )
    )
}