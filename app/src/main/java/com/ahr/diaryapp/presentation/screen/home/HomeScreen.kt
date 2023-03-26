package com.ahr.diaryapp.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.ahr.diaryapp.R
import com.ahr.diaryapp.data.repository.DiariesResponse
import com.ahr.diaryapp.util.RequestState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    drawerState: DrawerState,
    onMenuClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    navigateToWriteScreen: () -> Unit,
    diaries: DiariesResponse,
    navigateToWriteScreenWithArgs: (String) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    HomeNavigationDrawer(drawerState = drawerState, onSignOutClicked = onSignOutClicked) {
        Scaffold(
            topBar = {
                HomeTopAppBar(
                    scrollBehavior = scrollBehavior,
                    onMenuClicked = onMenuClicked,
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = navigateToWriteScreen) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.add_new_diary),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            content = {
                when (diaries) {
                    is RequestState.Loading -> {
                        HomeLoadingContent(
                            modifier = Modifier.padding(it)
                        )
                    }
                    is RequestState.Success -> {
                        HomeContent(
                            diariesMap = diaries.data,
                            onDiaryClicked = navigateToWriteScreenWithArgs,
                            modifier = Modifier.padding(it)
                        )
                    }
                    is RequestState.Error -> {
                        EmptyPage(
                            title = "Error",
                            subtitle = diaries.error.message.toString()
                        )
                    }
                    else -> {}
                }
            }
        )
    }
}

@Composable
fun HomeLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
