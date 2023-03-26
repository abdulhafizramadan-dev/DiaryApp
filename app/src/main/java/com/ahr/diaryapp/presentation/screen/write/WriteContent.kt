package com.ahr.diaryapp.presentation.screen.write

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahr.diaryapp.model.Mood

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WriteContent(
    pagerState: PagerState,
    title: String,
    onTitleChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    mood: Mood,
    onMoodChanged: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = mood) {
        pagerState.animateScrollToPage(mood.ordinal)
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
        TextField(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = title,
            onValueChange = onTitleChanged,
            placeholder = { Text(text = "Title") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Unspecified,
                disabledIndicatorColor = Color.Unspecified,
                unfocusedIndicatorColor = Color.Unspecified,
                placeholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {}
            ),
            maxLines = 1,
            singleLine = true
        )
        TextField(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = description,
            onValueChange = onDescriptionChanged,
            placeholder = { Text(text = "Tell me about it.") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Unspecified,
                disabledIndicatorColor = Color.Unspecified,
                unfocusedIndicatorColor = Color.Unspecified,
                placeholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {}
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {},
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.height(54.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Save")
        }
    }
}