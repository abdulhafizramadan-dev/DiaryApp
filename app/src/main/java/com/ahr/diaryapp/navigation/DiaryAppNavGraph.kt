package com.ahr.diaryapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ahr.diaryapp.R
import com.ahr.diaryapp.presentation.screen.authentication.AuthenticationScreen
import com.ahr.diaryapp.presentation.screen.authentication.AuthenticationViewModel
import com.ahr.diaryapp.presentation.screen.home.HomeScreen
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun DiaryAppNavGraph(
    navController: NavHostController,
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authenticationScreen(
            navigateToHomeScreen = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        )
        homeScreen(
            navigateToWriteScreen = {
                navController.navigate(Screen.Write.route)
            }
        )
        writeScreen()
    }

}

fun NavGraphBuilder.authenticationScreen(
    navigateToHomeScreen: () -> Unit
) {

    composable(route = Screen.Authentication.route) {

        val authenticationViewModel: AuthenticationViewModel = viewModel()
        val authenticated = authenticationViewModel.authenticated
        val signingLoadingState = authenticationViewModel.signingLoadingState
        val oneTapSignInState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        val appId = stringResource(id = R.string.app_id)

        AuthenticationScreen(
            authenticated = authenticated,
            oneTapSignInState = oneTapSignInState,
            messageBarState = messageBarState,
            loadingState = signingLoadingState,
            onButtonClicked = {
                authenticationViewModel.updateSigningLoadingState(true)
                oneTapSignInState.open()
            },
            onTokenIdReceived = { tokenId ->
                authenticationViewModel.signingWithGoogleMongoDbAtlas(
                    appId = appId,
                    tokenId = tokenId,
                    onSuccess = { isSigningSuccess ->
                        authenticationViewModel.updateSigningLoadingState(false)
                        if (isSigningSuccess) {
                            messageBarState.addSuccess("Signing success!")
                        } else {
                            messageBarState.addError(Exception("Signing failed!"))
                        }
                    },
                    onError = { exception ->
                        authenticationViewModel.updateSigningLoadingState(false)
                        messageBarState.addError(exception)
                    }
                )
            },
            onDialogDismissed = { message ->
                authenticationViewModel.updateSigningLoadingState(false)
                messageBarState.addError(Exception(message))
            },
            navigateToHomeScreen = navigateToHomeScreen
        )
    }
}

fun NavGraphBuilder.homeScreen(
    navigateToWriteScreen: () -> Unit
) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            onMenuClicked = {},
            navigateToWriteScreen = navigateToWriteScreen
        )
    }
}

fun NavGraphBuilder.writeScreen() {
    composable(
        route = Screen.Write.route,
        arguments = listOf(
            navArgument(name = Screen.WRITE_SCREEN_DIARY_ID_KEY) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) {

    }
}
