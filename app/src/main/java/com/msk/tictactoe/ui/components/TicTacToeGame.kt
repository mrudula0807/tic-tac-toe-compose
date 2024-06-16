package com.msk.tictactoe.ui.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        StatusText(status)
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
                            TicTacToeBox(board[i][j], isGameActive) {
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
                            }
                        }
                    }
                }
            }
            winningLine?.let { WinningLine(it, currentPlayer) }
        }
        Spacer(modifier = Modifier.height(36.dp))
        if (showResetButton) {
            ResetButton(viewModel)
        }
    }
}