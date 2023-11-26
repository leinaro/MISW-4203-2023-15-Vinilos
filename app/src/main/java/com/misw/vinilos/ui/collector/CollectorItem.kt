package com.misw.vinilos.ui.collector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.misw.vinilos.data.model.Collector
import com.misw.vinilos.ui.theme.VinilosTheme

@Composable
fun CollectorItemView(
    collector: Collector,
    onCollectorSelected: (Collector) -> Unit = {},
){
    ListItem(
        modifier = Modifier
            .clickable(
                onClickLabel = "Click en ${collector.name}",
            ) { onCollectorSelected(collector) },
        headlineContent = {
            Text(
                text = collector.name,
            )
        },
        supportingContent = {
            Row (
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email ${collector.name}")
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    textAlign = TextAlign.Center,
                    text = collector.email,
                )
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = Icons.Filled.Phone, contentDescription = "Phone  ${collector.name}")
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    text = collector.telephone,
                )
            }
        },
        //colors = ListItemDefaults.colors(
        //  containerColor = MaterialTheme.colorScheme.inverseOnSurface
        //),
    )
}

@Composable
@Preview(showBackground = true)
fun CollectorItemPreview() {
    VinilosTheme(darkTheme = true) {
        CollectorItemView(
            Collector(
                id = 1,
                name = "Collector name",
                telephone = "+573102178976",
                email = "j.monsalve@gmail.com"
            )
        )
    }
}