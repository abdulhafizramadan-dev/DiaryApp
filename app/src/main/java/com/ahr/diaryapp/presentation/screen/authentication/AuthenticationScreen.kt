package com.ahr.diaryapp.presentation.screen.authentication

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ahr.diaryapp.R
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    oneTapSignInState: OneTapSignInState,
    messageBarState: MessageBarState,
    loadingState: Boolean,
    onButtonClicked: () -> Unit
) {

    OneTapSignInWithGoogle(
        state = oneTapSignInState,
        clientId = stringResource(id = R.string.client_id),
        onTokenIdReceived = {
            Log.d("TAG", "AuthenticationScreen: Auth Token = $it")
            messageBarState.addSuccess("Sign in with google successfully")
        },
        onDialogDismissed = {
            messageBarState.addError(Exception(it))
        }
    )

    Scaffold { contentPadding ->
        ContentWithMessageBar(
            messageBarState = messageBarState,
            modifier = Modifier.padding(contentPadding)
        ) {
            AuthenticationContent(
                modifier = Modifier.padding(contentPadding),
                loadingState = loadingState,
                onButtonClicked = onButtonClicked
            )
        }
    }
}