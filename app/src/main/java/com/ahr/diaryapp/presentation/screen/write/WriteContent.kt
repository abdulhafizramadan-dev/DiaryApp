package com.ahr.diaryapp.presentation.screen.write

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahr.diaryapp.R
import com.ahr.diaryapp.model.Mood
import com.ahr.diaryapp.presentation.component.DiaryTextField

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WriteContent(
    pagerState: PagerState,
    title: String,
    onTitleChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    mood: Mood,
    onMoodChanged: (String) -> Unit,
    onSaveClicked: () -> Unit
) {

    val scrollState = rememberScrollState()
    val moodCurrentPage by remember { derivedStateOf { pagerState.currentPage } }

    var isTitleInvalid by remember { mutableStateOf(false) }
    var isDescriptionInvalid by remember { mutableStateOf(false) }

    val buttonSaveEnabled by remember(key1 = title, key2 = description) {
        derivedStateOf { title.isNotBlank() && description.isNotEmpty() }
    }

    LaunchedEffect(key1 = Unit) {
        pagerState.animateScrollToPage(mood.ordinal)
    }

    LaunchedEffect(key1 = moodCurrentPage) {
        onMoodChanged(Mood.values()[moodCurrentPage].name)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 14.dp)
            .verticalScroll(scrollState)
            .then(modifier)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        HorizontalPager(
            state = pagerState,
            pageCount = Mood.values().size,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Mood.values()[page].icon)
                    .crossfade(true)
                    .build(),
                contentDescription = "Mood image",
                modifier = Modifier.size(120.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        DiaryTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = title,
            onTextChanged = {
                isTitleInvalid = it.isEmpty()
                onTitleChanged(it)
            },
            placeholder = R.string.title,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {}
            ),
            singleLine = true,
            error = isTitleInvalid,
            errorMessage = R.string.title_invalid
        )
        DiaryTextField(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
            text = description,
            onTextChanged = {
                isDescriptionInvalid = it.isEmpty()
                onDescriptionChanged(it)
            },
            placeholder = R.string.tell_me_about_it,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {}
            ),
            error = isDescriptionInvalid,
            errorMessage = R.string.description_invalid
        )
        Spacer(modifier = Modifier.height(14.dp))
        Button(
            onClick = onSaveClicked,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .height(54.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            enabled = buttonSaveEnabled
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}