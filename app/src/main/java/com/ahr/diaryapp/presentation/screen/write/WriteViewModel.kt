package com.ahr.diaryapp.presentation.screen.write

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.diaryapp.data.repository.MongoRepository
import com.ahr.diaryapp.model.Diary
import com.ahr.diaryapp.model.Mood
import com.ahr.diaryapp.navigation.Screen
import com.ahr.diaryapp.util.RequestState
import com.ahr.diaryapp.util.toInstant
import com.ahr.diaryapp.util.toRealmInstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId
import java.util.*
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
                mongoRepository.getSelectedDiary(ObjectId(writeUiState.selectedDiaryId!!)).collect { diary ->
                    if (diary is RequestState.Success) {
                        updateDiary(diary.data)
                        updateTitle(diary.data.title)
                        updateDescription(diary.data.description)
                        updateMood(diary.data.mood)
                        updateDate(Date.from(diary.data.date.toInstant()))
                    }
                }
            }
        }
    }

    private suspend fun insertDiary(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
    ) {
        val diary = Diary(
            writeUiState.mood,
            writeUiState.title,
            writeUiState.description
        )
        val insertResult = mongoRepository.insertDiary(diary)
        if (insertResult is RequestState.Success) {
            withContext(Dispatchers.Main) {
                onSuccess("Success insert diary!")
            }
        } else if (insertResult is RequestState.Error) {
            withContext(Dispatchers.Main) {
                onError(insertResult.error.message.toString())
            }
        }
    }

    private suspend fun updateDiary(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
    ) {
        val selectedDiary = writeUiState.selectedDiary
        if (selectedDiary != null) {
            val diary = Diary().apply {
                _id = selectedDiary._id
                mood = writeUiState.mood.name
                title = writeUiState.title
                description = writeUiState.description
                date = writeUiState.date.toInstant().toRealmInstant()
            }
            val insertResult = mongoRepository.updateUpdate(diary)
            if (insertResult is RequestState.Success) {
                withContext(Dispatchers.Main) {
                    onSuccess("Success update diary!")
                }
            } else if (insertResult is RequestState.Error) {
                withContext(Dispatchers.Main) {
                    onError(insertResult.error.message.toString())
                }
            }
        }
    }

    fun upsertDiary(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (writeUiState.selectedDiaryId != null) {
                updateDiary(onSuccess = onSuccess, onError = onError)
            } else {
                insertDiary(onSuccess = onSuccess, onError = onError)
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

    fun updateDate(date: Date) {
        writeUiState = writeUiState.copy(
            date = date
        )
    }

    private fun updateDiary(diary: Diary) {
        writeUiState = writeUiState.copy(
            selectedDiary = diary
        )
    }

}