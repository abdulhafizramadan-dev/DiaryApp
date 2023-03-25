package com.ahr.diaryapp.util

sealed interface RequestState<out T> {
    object Idle : RequestState<Nothing>
    object Loading : RequestState<Nothing>
    class Success<T>(val data: T) : RequestState<T>
    class Error(val error: Throwable) : RequestState<Nothing>
}