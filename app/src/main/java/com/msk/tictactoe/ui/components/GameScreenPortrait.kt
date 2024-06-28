package com.msk.tictactoe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.msk.tictactoe.utils.Dimens
import com.msk.tictactoe.utils.GameAccessibilityAnnouncer
import com.msk.tictactoe.viewmodel.TicTacToeViewModel

@Composable
fun GameScreenPortrait(
    viewModel: TicTacToeViewModel,
    board: Array<Array<String?>>,
    currentPlayer: String,
    isGameActive: Boolean,
    status: String,
    showResetButton: Boolean,
    winningLine: List<Pair<Int, Int>>?
) {
    //integrate the accessibility announcer
    GameAccessibilityAnnouncer(viewModel = viewModel)
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
        GameGrid(viewModel, board, currentPlayer, isGameActive, winningLine)
        Spacer(modifier = Modifier.height(Dimens.paddingLarge))
        Box(
            modifier = Modifier
                .height(Dimens.paddingVeryLarge)
                .width(Dimens.boxSize),
            contentAlignment = Alignment.Center
        ) {
            ResetButton(viewModel, showResetButton)
        }
    }
}