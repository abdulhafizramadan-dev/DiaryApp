package com.ahr.diaryapp.presentation.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ahr.diaryapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMenuClicked: () -> Unit,
    navigateToWriteScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            HomeTopAppBar(
                onMenuClicked = onMenuClicked
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToWriteScreen) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.add_new_diary),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        content = {}
    )
}