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
import com.ahr.diaryapp.model.Mood
import com.ahr.diaryapp.ui.theme.DiaryAppTheme
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WriteScreen(
    pagerState: PagerState,
    onNavigationIconClicked: () -> Unit,
    onDeleteConfirmed: () -> Unit,
    diary: Diary? = null,
    title: String,
    description: String,
    mood: Mood,
    date: Date,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onMoodChanged: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            WriteTopAppBar(
                onNavigationIconClicked = onNavigationIconClicked,
                onDeleteConfirmed = onDeleteConfirmed,
                selectedDiary = diary,
                mood = mood,
                date = date
            )
        }
    ) {
        WriteContent(
            pagerState = pagerState,
            title = title,
            description = description,
            mood = mood,
            onTitleChanged = onTitleChanged,
            onDescriptionChanged = onDescriptionChanged,
            onMoodChanged = onMoodChanged,
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
            },
            title = "",
            description = "",
            mood = Mood.Neutral,
            date = Date(),
            onTitleChanged = {},
            onDescriptionChanged = {},
            onMoodChanged = {}
        )
    }
}
