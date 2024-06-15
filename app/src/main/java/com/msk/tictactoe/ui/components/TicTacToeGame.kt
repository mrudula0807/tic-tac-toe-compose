package com.msk.tictactoe.ui.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.msk.tictactoe.TicTacToeViewModel

@Composable
fun TicTacToeGame(viewModel: TicTacToeViewModel) {
    val board by viewModel.board
    val currentPlayer by viewModel.currentPlayer
    val isGameActive by viewModel.isGameActive
    val status by viewModel.status
    val showResetButton by viewModel.showResetButton
    val winningLine by viewModel.winningLine

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = status, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.size(316.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                //rows
                for (i in 0..2) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        //columns
                        for (j in 0..2) {
                            //represents each grid that can be clicked to play
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                                    .padding(16.dp)
                                    .clickable(enabled = isGameActive) {
                                        viewModel.playMove(i, j) {
                                            Handler(Looper.getMainLooper()).postDelayed({
                                                viewModel.autoPlay {
                                                    val autoResult = viewModel.isWin(board, "O")
                                                    if (autoResult.first) {
                                                        viewModel.gameLost(autoResult.second)
                                                    } else if (viewModel.isBoardFull(board)) {
                                                        viewModel.gameDraw()
                                                    } else {
                                                        viewModel.gameWon()
                                                    }
                                                }
                                            }, 500) // Simulate some delay for the computer move
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = board[i][j] ?: "", fontSize = 36.sp)
                            }
                        }
                    }
                }
            }
            winningLine?.let { line ->
                Canvas(modifier = Modifier.matchParentSize()) {
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
        }
        Spacer(modifier = Modifier.height(36.dp))
        if (showResetButton) {
            Button(onClick = { viewModel.resetGame() }) {
                Text(text = "Reset")
            }
        }
    }
}