package com.ahr.diaryapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.diaryapp.data.repository.DiariesResponse
import com.ahr.diaryapp.data.repository.MongoRepository
import com.ahr.diaryapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mongoRepository: MongoRepository
) : ViewModel() {

    private val _diariesMapped = MutableStateFlow<DiariesResponse>(RequestState.Idle)
    val diariesMapped get() = _diariesMapped.asStateFlow()

    init {
        observeAllDiaries()
    }

    private fun observeAllDiaries() {
        viewModelScope.launch {
            mongoRepository.getAllDiaries().collect { diariesResponse ->
                _diariesMapped.value = diariesResponse
            }
        }
    }

}