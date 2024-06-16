package com.msk.tictactoe.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun WinningLine(line: List<Pair<Int, Int>>, currentPlayer: String) {
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
            color = if (currentPlayer == "X") Color.Green else Color.Red,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )
    }
}