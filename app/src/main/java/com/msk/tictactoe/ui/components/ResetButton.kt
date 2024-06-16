package com.msk.tictactoe.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.msk.tictactoe.TicTacToeViewModel

@Composable
fun ResetButton(viewModel: TicTacToeViewModel) {
    Button(onClick = { viewModel.resetGame() }) {
        Text(text = "Reset", color = MaterialTheme.colorScheme.onPrimary)
    }
}
