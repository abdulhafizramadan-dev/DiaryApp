package com.ahr.diaryapp.presentation.component

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ahr.diaryapp.R
import com.ahr.diaryapp.ui.theme.DiaryAppTheme

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    loadingState: Boolean = false,
    @StringRes primaryText: Int = R.string.signing_with_google,
    @StringRes secondaryText: Int = R.string.please_wait,
    icon: Int = R.drawable.google_logo,
    shape: Shape = MaterialTheme.shapes.extraSmall,
    borderColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderStrokeWidth: Dp = 1.dp,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {

    var buttonText by remember {
        mutableStateOf(primaryText)
    }

    LaunchedEffect(key1 = loadingState) {
        buttonText = if (!loadingState) primaryText else secondaryText
    }

    Surface(
        modifier = modifier.clickable(enabled = !loadingState, onClick = onClick),
        shape = shape,
        border = BorderStroke(width = borderStrokeWidth, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = buttonText),
                style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize)
            )
            if (loadingState) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGoogleButton() {
    DiaryAppTheme {
        GoogleButton {}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGoogleButtonLoading() {
    DiaryAppTheme {
        GoogleButton(loadingState = true) {}
    }
}
