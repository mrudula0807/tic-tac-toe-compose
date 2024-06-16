package com.msk.tictactoe.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun StatusText(status: String) {
    val animatedColor = animateColorAsState(
        targetValue = if (status.contains("lost")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
        label = ""
    )
    Text(
        text = status,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = animatedColor.value
    )
}