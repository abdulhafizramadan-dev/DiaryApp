package com.ahr.diaryapp.presentation.screen.write

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.ahr.diaryapp.R
import com.ahr.diaryapp.model.Diary
import com.ahr.diaryapp.model.Mood
import com.ahr.diaryapp.presentation.component.DisplayAlertDialog
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteTopAppBar(
    onNavigationIconClicked: () -> Unit,
    onDeleteConfirmed: () -> Unit,
    selectedDiary: Diary? = null,
    mood: Mood,
    date: Date
) {

    val topAppBarDate = remember {
        SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            .format(date)
    }

    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigationIconClicked) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back to previous screen"
                )
            }
        },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = mood.name,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = topAppBarDate,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    textAlign = TextAlign.Center
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            if (selectedDiary != null) {
                DeleteDiaryAction(
                    selectedDiary = selectedDiary,
                    onDeleteConfirmed = onDeleteConfirmed
                )
            }
        }
    )
}

@Composable
fun DeleteDiaryAction(
    selectedDiary: Diary,
    onDeleteConfirmed: () -> Unit
) {
    var dropdownMenuExpanded by remember { mutableStateOf(false) }
    var alertDialogOpened by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = dropdownMenuExpanded,
        onDismissRequest = { dropdownMenuExpanded = !dropdownMenuExpanded }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.delete)) },
            onClick = {
                alertDialogOpened = true
                dropdownMenuExpanded = false
            }
        )
    }

    DisplayAlertDialog(
        title = R.string.delete,
        message = stringResource(id = R.string.delete_description, selectedDiary.title),
        dialogOpened = alertDialogOpened,
        onDialogClosed = { alertDialogOpened = false },
        onDialogConfirmed = onDeleteConfirmed
    )

    IconButton(onClick = { dropdownMenuExpanded = !dropdownMenuExpanded }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(R.string.more_options),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

