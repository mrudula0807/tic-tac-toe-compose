package com.msk.tictactoe.ui.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.msk.tictactoe.R
import com.msk.tictactoe.utils.Dimens
import com.msk.tictactoe.viewmodel.TicTacToeViewModel

@Composable
fun GameGrid(
    viewModel: TicTacToeViewModel,
    board: Array<Array<String?>>,
    currentPlayer: String,
    isGameActive: Boolean,
    winningLine: List<Pair<Int, Int>>?
) {
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
                        TicTacToeBox(
                            board[i][j], isGameActive,
                            stringResource(
                                R.string.content_desc_cell,
                                i + 1,
                                j + 1,
                                (if (board[i][j].isNullOrEmpty()) stringResource(R.string.empty_cell)
                                else board[i][j])!!
                            )
                        ) {
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
}