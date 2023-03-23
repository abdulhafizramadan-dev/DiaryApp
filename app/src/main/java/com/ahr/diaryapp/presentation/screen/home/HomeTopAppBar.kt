package com.ahr.diaryapp.presentation.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ahr.diaryapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    onMenuClicked: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.menu_icon)
                )
            }
        },
        title = {
            Text(text = stringResource(R.string.diary))
        },
        actions = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Date icon"
                )
            }
        }
    )
}