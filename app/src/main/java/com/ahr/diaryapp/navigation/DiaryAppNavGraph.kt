package com.ahr.diaryapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ahr.diaryapp.presentation.screen.authentication.AuthenticationScreen
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
        authenticationScreen()
        homeScreen()
        writeScreen()
    }

}

fun NavGraphBuilder.authenticationScreen() {
    composable(
        route = Screen.Authentication.route
    ) {

        val oneTapSignInState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        AuthenticationScreen(
            oneTapSignInState = oneTapSignInState,
            messageBarState = messageBarState,
            loadingState = oneTapSignInState.opened,
            onButtonClicked = {
                oneTapSignInState.open()
            }
        )
    }
}

fun NavGraphBuilder.homeScreen() {
    composable(
        route = Screen.Home.route
    ) {

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
