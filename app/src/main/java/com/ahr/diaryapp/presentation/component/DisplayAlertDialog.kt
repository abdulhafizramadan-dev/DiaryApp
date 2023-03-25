package com.ahr.diaryapp.presentation.component

import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.ahr.diaryapp.R

@Composable
fun DisplayAlertDialog(
    @StringRes title: Int,
    message: String,
    dialogOpened: Boolean,
    onDialogClosed: () -> Unit,
    onDialogConfirmed: () -> Unit
) {
    if (dialogOpened) {
        AlertDialog(
            title = {
                Text(
                    text = stringResource(id = title),
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDialogConfirmed()
                        onDialogClosed()
                    })
                {
                    Text(text = stringResource(R.string.yes))
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDialogClosed) {
                    Text(text = stringResource(R.string.no))
                }
            },
            onDismissRequest = onDialogClosed
        )
    }
}