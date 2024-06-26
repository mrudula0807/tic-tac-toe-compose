package com.msk.tictactoe.ui.components

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import com.msk.tictactoe.viewmodel.TicTacToeViewModel

@Composable
fun TicTacToeGame(viewModel: TicTacToeViewModel) {
    val board by viewModel.board
    val currentPlayer by viewModel.currentPlayer
    val isGameActive by viewModel.isGameActive
    val status by viewModel.status
    val showResetButton by viewModel.showResetButton
    val winningLine by viewModel.winningLine

    val isLandscape = LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE

    if (isLandscape) {
        GameScreenLandscape(
            viewModel,
            board,
            currentPlayer,
            isGameActive,
            status,
            showResetButton,
            winningLine
        )
    } else {
        GameScreenPortrait(
            viewModel,
            board,
            currentPlayer,
            isGameActive,
            status,
            showResetButton,
            winningLine
        )
    }
}