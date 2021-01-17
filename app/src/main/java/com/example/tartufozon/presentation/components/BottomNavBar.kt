package com.example.tartufozon.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun BuildBottomNavBar(screens : List<Fragmentz>) {

    BottomNavigation(
        elevation = 12.dp
    ) {
        screens.forEach {
            BottomNavigationItem(
                icon = { Icon(it.icon) },
                selected = false,
                label = { Text(text = it.label) },
                onClick = {
                    Timber.d("BuildBottomNavBar: ") //NOT WORKING
                })
        }
    }
}


