package com.ahr.diaryapp.presentation.screen.write

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ahr.diaryapp.model.Diary
import com.ahr.diaryapp.ui.theme.DiaryAppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WriteScreen(
    pagerState: PagerState,
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
        WriteContent(
            pagerState = pagerState,
            title = "",
            onTitleChanged = {},
            description = "",
            onDescriptionChanged = {},
            modifier = Modifier.padding(it)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PreviewWriteScreen() {
    DiaryAppTheme {
        val pagerState = rememberPagerState()
        WriteScreen(
            pagerState = pagerState,
            onNavigationIconClicked = {},
            onDeleteConfirmed = {},
            diary = Diary().apply {
                title = "Title"
                description = "Description"
            }
        )
    }
}
