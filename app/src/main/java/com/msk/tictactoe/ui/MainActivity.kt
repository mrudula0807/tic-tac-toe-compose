package com.msk.tictactoe.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.msk.tictactoe.ui.components.TicTacToeGame
import com.msk.tictactoe.ui.theme.TicTacToeTheme
import com.msk.tictactoe.viewmodel.TicTacToeViewModel
import com.msk.tictactoe.viewmodel.TicTacToeViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: TicTacToeViewModel by viewModels { TicTacToeViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setFullScreen()
        setContent {
            TicTacToeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicTacToeGame(viewModel)
                }
            }
        }
    }

    private fun setFullScreen(){
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.hide(WindowInsetsCompat.Type.systemBars())
        insetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}