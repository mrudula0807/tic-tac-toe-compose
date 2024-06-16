package com.msk.tictactoe.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.msk.tictactoe.ui.theme.emerald
import com.msk.tictactoe.ui.theme.satin

@Composable
fun WinningLine(line: List<Pair<Int, Int>>, currentPlayer: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedAlpha = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Canvas(modifier = Modifier.fillMaxSize()) {
        val cellSize = 100.dp.toPx()
        val spacing = 8.dp.toPx()
        val halfCellSize = cellSize / 2

        val startCell = line.first()
        val endCell = line.last()

        // Calculate the coordinates for the line within the grid
        val startX = startCell.second * (cellSize + spacing) + halfCellSize
        val startY = startCell.first * (cellSize + spacing) + halfCellSize
        val endX = endCell.second * (cellSize + spacing) + halfCellSize
        val endY = endCell.first * (cellSize + spacing) + halfCellSize

        drawLine(
            color = (if (currentPlayer == "X") emerald else satin).copy(alpha = animatedAlpha.value),
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )
    }
}