package com.ahr.diaryapp.presentation.screen.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.GoogleAuthType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel : ViewModel() {

    var signingLoadingState by mutableStateOf(false)
    private set

    fun updateSigningLoadingState(state: Boolean) {
        signingLoadingState = state
    }

    fun signingWithGoogleMongoDbAtlas(
        appId: String,
        tokenId: String,
        onSuccess: (Boolean) -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val mongoApp = App.create(appId)
                val result = withContext(Dispatchers.IO) {
                    mongoApp.login(
                        Credentials.google(token = tokenId, type = GoogleAuthType.ID_TOKEN)
                    ).loggedIn
                }
                withContext(Dispatchers.Main) {
                    onSuccess(result)
                }
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    onError(exception)
                }
            }
        }
    }

}