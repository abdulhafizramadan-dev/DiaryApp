package com.ahr.diaryapp.presentation.screen.authentication

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    loadingState: Boolean,
    onButtonClicked: () -> Unit
) {
    Scaffold { contentPadding ->
        AuthenticationContent(
            modifier = Modifier.padding(contentPadding),
            loadingState = loadingState,
            onButtonClicked = onButtonClicked
        )
    }
}