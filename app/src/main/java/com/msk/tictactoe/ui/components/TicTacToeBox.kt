package com.msk.tictactoe.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TicTacToeBox(boardContent: String?, isGameActive: Boolean, onPlayed: () -> Unit) {
    val scale = remember { Animatable(1f) }
    LaunchedEffect(boardContent) {
        if (boardContent != null) {
            scale.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(durationMillis = 100, easing = LinearEasing)
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 100, easing = LinearEasing)
            )
        }
    }
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
            .clickable(enabled = isGameActive, onClick = onPlayed)
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value),
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = boardContent ?: "",
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}