package com.msk.tictactoe.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.msk.tictactoe.TicTacToeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ResetButton(viewModel: TicTacToeViewModel, showResetButton: Boolean) {
    AnimatedVisibility(
        visible = showResetButton,
        enter = fadeIn(animationSpec = tween(500)) + slideInVertically(animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(animationSpec = tween(500))
    ){
        Button(onClick = { viewModel.resetGame() }) {
            Text(text = "Reset", color = MaterialTheme.colorScheme.onPrimary)
        }
    }

}
