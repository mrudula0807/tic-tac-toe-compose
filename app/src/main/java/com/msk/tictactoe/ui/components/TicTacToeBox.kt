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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.msk.tictactoe.utils.Dimens

@Composable
fun TicTacToeBox(
    boardContent: String?,
    row: Int,
    col: Int,
    isGameActive: Boolean,
    onPlayed: () -> Unit
) {
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
            .size(Dimens.boxSize)
            .background(
                MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(Dimens.paddingSmall)
            )
            .padding(Dimens.paddingMedium)
            .clickable(enabled = isGameActive, onClick = onPlayed)
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            .semantics { contentDescription = "Cell $row,$col: ${boardContent ?: "Empty"}" },
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = boardContent ?: "",
            fontSize = Dimens.textSizeVeryLarge,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}