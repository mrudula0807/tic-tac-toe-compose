package com.msk.tictactoe.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import com.msk.tictactoe.utils.Dimens

@Composable
fun StatusText(status: String) {
    val animatedColor = animateColorAsState(
        targetValue = if (status.contains("lost")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
        label = ""
    )
    Text(
        text = status,
        fontSize = Dimens.textSizeLarge,
        fontWeight = FontWeight.Bold,
        color = animatedColor.value,
        modifier = Modifier.semantics { contentDescription = status }
    )
}