package com.ahr.diaryapp.presentation.screen.write

import com.ahr.diaryapp.model.Diary
import com.ahr.diaryapp.model.Mood
import java.util.Date

data class WriteUiState(
    val selectedDiaryId: String? = null,
    val selectedDiary: Diary? = null,
    val title: String = "",
    val description: String = "",
    val mood: Mood = Mood.Neutral,
    val date: Date = Date()
)
