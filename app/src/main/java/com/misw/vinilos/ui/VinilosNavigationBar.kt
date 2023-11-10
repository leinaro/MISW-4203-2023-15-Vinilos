package com.misw.vinilos.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.misw.vinilos.R
import com.misw.vinilos.ui.theme.VinilosTheme


data class NavigationItem(
    val icon: Painter,
    val label: String,
)

@Composable
fun VinilosNavigationBar(
    onItemSelected: (Int) -> Unit = {},
    selected: Int = 0,
) {
    val items = listOf(
        NavigationItem(
            icon = painterResource(id = R.drawable.ic_albums),
            label = "albums",
        ),
        NavigationItem(
            icon = painterResource(id = R.drawable.ic_artists),
            label = "artistas",
        ),
        NavigationItem(
            icon = painterResource(id = R.drawable.ic_collectors),
            label = "colecciones",
        ),
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        items.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = navigationItem.icon,
                        contentDescription = navigationItem.label,
                        modifier = Modifier.size(24.dp),
                        tint = if (selected == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    ) },
                label = {
                    Text(
                        text=navigationItem.label,
                        color = if (selected == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground)
                        },
                selected = selected == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VinilosNavigationBarPreview() {
    VinilosTheme(dynamicColor = false, darkTheme = false) {
        VinilosNavigationBar(
            selected = 2
        )
    }
}