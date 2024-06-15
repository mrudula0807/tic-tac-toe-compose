package com.msk.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msk.tictactoe.ui.components.TicTacToeGame
import com.msk.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: TicTacToeViewModel = viewModel()
                    TicTacToeGame(viewModel)
                }
            }
        }
    }
}