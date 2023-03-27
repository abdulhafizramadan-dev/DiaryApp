package com.ahr.diaryapp.presentation.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ahr.diaryapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    @StringRes placeholder: Int,
    @StringRes errorMessage: Int = R.string.default_textfield_error_message,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    error: Boolean = false
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = text,
            onValueChange = onTextChanged,
            placeholder = { Text(text = stringResource(id = placeholder)) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Unspecified,
                disabledIndicatorColor = Color.Unspecified,
                unfocusedIndicatorColor = Color.Unspecified,
                placeholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            ),
            maxLines = if (singleLine) 1 else Int.MAX_VALUE,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            isError = error
        )
        if (error) {
            Text(
                text = stringResource(id = errorMessage),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}
