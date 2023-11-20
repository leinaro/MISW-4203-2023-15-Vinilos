package com.misw.vinilos.ui.collector

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.misw.vinilos.R.string
import com.misw.vinilos.Routes
import com.misw.vinilos.VinilosEvent
import com.misw.vinilos.VinilosViewModel
import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.ui.theme.VinilosTheme

@Composable
fun CollectorListScreen(collectorList: List<Collector>) {
    val composeView = LocalView.current
    val viewModel = composeView.findViewTreeViewModelStoreOwner()?.let {
        hiltViewModel<VinilosViewModel>(it)
    }

    LazyColumn(
        modifier = Modifier.padding(24.dp)
    ){
        item {
            Text(
                text = stringResource(string.collectors),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                //.padding(bottom = 8.dp, start = 10.dp, end = 10.dp, top = 10.dp)
            )
        }
        items(items = collectorList){ collector ->
            CollectorItemView(
                collector=collector,
                onCollectorSelected = {
                    viewModel?.setEvent(VinilosEvent.NavigateTo(
                        Routes.CollectorDetail.path.replace("{collectorId}",collector.id.toString())))
                }
            )
            Divider(color = Color.Black)
        }
    }
}

@Composable
@Preview
fun CollectionListScreenPreview() {
    VinilosTheme(darkTheme = true) {
        CollectorListScreen(
            listOf(
                Collector(
                    id = 1,
                    name = "Collector name",
                    telephone = "+573102178976",
                    email = "j.monsalve@gmail.com"
                ),
                Collector(
                    id = 1,
                    name = "Collector name",
                    telephone = "+573102178976",
                    email = "j.monsalve@gmail.com"
                )
            )
        )
    }
}
