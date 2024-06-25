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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.msk.tictactoe.TicTacToeViewModel
import com.msk.tictactoe.utils.Dimens

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
            .padding(Dimens.paddingMedium)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StatusText(status)
        Spacer(modifier = Modifier.height(Dimens.paddingMedium))
        Box(modifier = Modifier.size(Dimens.gridSize)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
            ) {
                //rows
                for (i in 0..2) {
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)) {
                        //columns
                        for (j in 0..2) {
                            //represents each grid that can be clicked to play
                            TicTacToeBox(board[i][j], i, j, isGameActive) {
                                viewModel.playMove(i, j) {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        viewModel.autoPlay()
                                    }, 500) // Simulate some delay for the computer move
                                }
                            }
                        }
                    }
                }
            }
            winningLine?.let { WinningLine(it, currentPlayer) }
        }
        Spacer(modifier = Modifier.height(Dimens.paddingLarge))
        Box(modifier = Modifier.height(Dimens.paddingVeryLarge)) {
            ResetButton(viewModel, showResetButton)
        }
    }
}