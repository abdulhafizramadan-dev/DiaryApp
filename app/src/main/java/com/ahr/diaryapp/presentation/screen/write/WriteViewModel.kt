package com.ahr.diaryapp.presentation.screen.write

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ahr.diaryapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var writeUiState by mutableStateOf(WriteUiState())
    private set

    init {
        getGetDiaryIdArgument()
    }

    private fun getGetDiaryIdArgument() {
        writeUiState = writeUiState.copy(
            selectedDiaryId = savedStateHandle[Screen.WRITE_SCREEN_DIARY_ID_KEY]
        )
    }

}