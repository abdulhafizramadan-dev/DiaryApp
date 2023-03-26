package com.ahr.diaryapp.presentation.screen.write

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.diaryapp.data.repository.MongoRepository
import com.ahr.diaryapp.model.Mood
import com.ahr.diaryapp.navigation.Screen
import com.ahr.diaryapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val mongoRepository: MongoRepository
) : ViewModel() {

    var writeUiState by mutableStateOf(WriteUiState())
    private set

    init {
        getGetDiaryIdArgument()
        getSelectedDiary()
    }

    private fun getGetDiaryIdArgument() {
        writeUiState = writeUiState.copy(
            selectedDiaryId = savedStateHandle[Screen.WRITE_SCREEN_DIARY_ID_KEY]
        )
    }

    private fun getSelectedDiary() {
        if (writeUiState.selectedDiaryId != null) {
            viewModelScope.launch {
                val diary = mongoRepository.getSelectedDiary(ObjectId(writeUiState.selectedDiaryId!!))
                if (diary is RequestState.Success) {
                    updateTitle(diary.data.title)
                    updateDescription(diary.data.description)
                    updateMood(diary.data.mood)
                }
            }
        }
    }

    fun updateTitle(title: String) {
        writeUiState = writeUiState.copy(
            title = title
        )
    }

    fun updateDescription(description: String) {
        writeUiState = writeUiState.copy(
            description = description
        )
    }

    fun updateMood(mood: String) {
        writeUiState = writeUiState.copy(
            mood = Mood.valueOf(mood)
        )
    }

}