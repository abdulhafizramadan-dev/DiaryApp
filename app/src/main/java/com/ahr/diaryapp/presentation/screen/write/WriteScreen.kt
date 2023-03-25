package com.ahr.diaryapp.presentation.screen.write

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ahr.diaryapp.model.Diary
import com.ahr.diaryapp.ui.theme.DiaryAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(
    onNavigationIconClicked: () -> Unit,
    onDeleteConfirmed: () -> Unit,
    diary: Diary? = null,
) {
    Scaffold(
        topBar = {
            WriteTopAppBar(
                onNavigationIconClicked = onNavigationIconClicked,
                onDeleteConfirmed = onDeleteConfirmed,
                selectedDiary = diary
            )
        }
    ) {
        Box(modifier = Modifier.padding(it))
    }
}

@Preview
@Composable
fun PreviewWriteScreen() {
    DiaryAppTheme {
        WriteScreen(
            onNavigationIconClicked = {},
            onDeleteConfirmed = {},
            diary = Diary().apply {
                title = "Title"
                description = "Description"
            }
        )
    }
}
