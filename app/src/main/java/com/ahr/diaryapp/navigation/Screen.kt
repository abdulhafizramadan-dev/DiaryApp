package com.ahr.diaryapp.navigation

sealed class Screen(val route: String) {

    object Authentication : Screen("authentication_screen")
    object Home : Screen("home_screen")
    object Write : Screen("write_screen?$WRITE_SCREEN_DIARY_ID_KEY={$WRITE_SCREEN_DIARY_ID_KEY}") {
        fun routeWithDiaryId(diaryId: String): String {
            return "write_screen?$WRITE_SCREEN_DIARY_ID_KEY=$diaryId"
        }
    }

    companion object {
        const val WRITE_SCREEN_DIARY_ID_KEY = "diary_id"
    }
}
