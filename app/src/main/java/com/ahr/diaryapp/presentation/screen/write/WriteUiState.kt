package com.ahr.diaryapp.presentation.screen.write

import com.ahr.diaryapp.model.Mood

data class WriteUiState(
    val selectedDiaryId: String? = null,
    val title: String = "",
    val description: String = "",
    val mood: Mood = Mood.Neutral,
)
