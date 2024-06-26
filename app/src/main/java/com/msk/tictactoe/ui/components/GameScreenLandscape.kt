package com.msk.tictactoe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.msk.tictactoe.utils.Dimens
import com.msk.tictactoe.viewmodel.TicTacToeViewModel

@Composable
fun GameScreenLandscape(
    viewModel: TicTacToeViewModel,
    board: Array<Array<String?>>,
    currentPlayer: String,
    isGameActive: Boolean,
    status: String,
    showResetButton: Boolean,
    winningLine: List<Pair<Int, Int>>?
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(Dimens.paddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StatusText(status)
        Spacer(modifier = Modifier.height(Dimens.paddingMedium))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingMedium)
                .verticalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            GameGrid(viewModel, board, currentPlayer, isGameActive, winningLine)
            Spacer(modifier = Modifier.width(Dimens.paddingLarge))
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
}