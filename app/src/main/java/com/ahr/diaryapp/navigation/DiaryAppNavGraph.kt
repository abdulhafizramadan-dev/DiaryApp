package com.ahr.diaryapp.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ahr.diaryapp.R
import com.ahr.diaryapp.presentation.component.DisplayAlertDialog
import com.ahr.diaryapp.presentation.screen.authentication.AuthenticationScreen
import com.ahr.diaryapp.presentation.screen.authentication.AuthenticationViewModel
import com.ahr.diaryapp.presentation.screen.home.HomeScreen
import com.ahr.diaryapp.presentation.screen.home.HomeViewModel
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App.Companion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            },
            navigateToAuthenticationScreen = {
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
            }
        )
        writeScreen()
    }

}

fun NavGraphBuilder.authenticationScreen(
    navigateToHomeScreen: () -> Unit
) {

    composable(route = Screen.Authentication.route) {

        val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
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

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeScreen(
    navigateToWriteScreen: () -> Unit,
    navigateToAuthenticationScreen: () -> Unit,
) {
    composable(route = Screen.Home.route) {

        val homeViewModel: HomeViewModel = hiltViewModel()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var signOutDialogOpened by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        val diariesMapped by homeViewModel.diariesMapped.collectAsState()

        val appId = stringResource(id = R.string.app_id)

        HomeScreen(
            diaries = diariesMapped,
            drawerState = drawerState,
            onMenuClicked = {
                scope.launch { drawerState.open() }
            },
            onSignOutClicked = {
                signOutDialogOpened = true
            },
            navigateToWriteScreen = navigateToWriteScreen
        )

        DisplayAlertDialog(
            title = R.string.sign_out_dialog_title,
            message = R.string.sign_out_dialog_message,
            dialogOpened = signOutDialogOpened,
            onDialogClosed = { signOutDialogOpened = false },
            onDialogConfirmed = {
                scope.launch {
                    withContext(Dispatchers.IO) {
                        val user = Companion.create(appId).currentUser
                        if (user != null) {
                            user.logOut()
                            withContext(Dispatchers.Main) {
                                navigateToAuthenticationScreen()
                            }
                        }
                    }
                }
            }
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
