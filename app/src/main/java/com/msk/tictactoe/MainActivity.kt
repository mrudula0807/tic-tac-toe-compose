package com.msk.tictactoe

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.msk.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicTacToeGame()
                }
            }
        }
    }

    @Composable
    fun TicTacToeGame() {
        var board by remember { mutableStateOf(Array(3) { arrayOfNulls<String>(3) }) }
        var currentPlayer by remember { mutableStateOf("X") }
        var isGameActive by remember { mutableStateOf(true) }
        var status by remember { mutableStateOf("You start") }
        var showResetButton by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = status, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            //rows
            for (i in 0..2) {
                Row {
                    //columns
                    for (j in 0..2) {
                        //represents each grid that can be clicked to play
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                                .padding(16.dp)
                                .clickable(enabled = isGameActive) {
                                    if (board[i][j] == null) {
                                        board[i][j] = currentPlayer
                                        if (isWin(board, currentPlayer)) {
                                            status = getString(R.string.game_status_won)
                                            isGameActive = false
                                            showResetButton = true
                                        } else if (isBoardFull(board)) {
                                            status = getString(R.string.game_status_draw)
                                            isGameActive = false
                                            showResetButton = true
                                        } else {
                                            //box disabled to prevent multiple movements by player one
                                            isGameActive = false
                                            currentPlayer = "O"
                                            status = ""
                                            Handler(Looper.getMainLooper()).postDelayed({
                                                autoPlay(board) {
                                                    if (isWin(board, currentPlayer)) {
                                                        status =
                                                            getString(R.string.game_status_lost)
                                                        isGameActive = false
                                                        showResetButton = true
                                                    } else if (isBoardFull(board)) {
                                                        status =
                                                            getString(R.string.game_status_draw)
                                                        isGameActive = false
                                                        showResetButton = true
                                                    } else {
                                                        currentPlayer = "X"
                                                        status = "Your turn"
                                                        //enable grids and allow player one to make next move
                                                        isGameActive = true
                                                    }
                                                }
                                            }, 500) // Simulate some delay for the computer move
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = board[i][j] ?: "", fontSize = 36.sp)
                        }
                        //space between grid columns
                        if (j < 2) Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                //space between grid rows
                if (i < 2) Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(36.dp))
            if (showResetButton) {
                Button(onClick = {
                    board = Array(3) { arrayOfNulls(3) }
                    currentPlayer = "X"
                    isGameActive = true
                    status = "Your turn"
                    showResetButton = false
                }) {
                    Text(text = "Reset")
                }
            }
        }
    }

    /*
    To check if the current player has any one complete row/column/diagonal selected
    */
    private fun isWin(board: Array<Array<String?>>, player: String): Boolean {
        for (i in 0..2) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true
        return false
    }

    /*
    Check if the board is full; if neither of the players have won yet and the board is full,
    it's draw*/
    private fun isBoardFull(board: Array<Array<String?>>): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == null) return false
            }
        }
        return true
    }

    private fun autoPlay(board: Array<Array<String?>>, onPlayed: () -> Unit) {
        //use an optimized algorithm for finding the best position for the next move of player 2
        val bestPos = getBestPosition(board)
        bestPos?.let { (i, j) ->
            board[i][j] = "O"
            onPlayed()
        }
    }

    fun getBestPosition(board: Array<Array<String?>>): Pair<Int, Int>? {
        var bestValue = Int.MIN_VALUE
        var bestPosition: Pair<Int, Int>? = null

        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == null) {
                    board[i][j] = "O"
                    val currentValue = minimax(board, 0, false)
                    board[i][j] = null
                    if (currentValue > bestValue) {
                        bestPosition = Pair(i, j)
                        bestValue = currentValue
                    }
                }
            }
        }
        return bestPosition
    }

    fun minimax(board: Array<Array<String?>>, depth: Int, isMax: Boolean): Int {
        val score = evaluatePatterns(board)
        if (score == 10) return score - depth
        if (score == -10) return score + depth
        if (isBoardFull(board)) return 0
        if (isMax) {
            var best = Int.MIN_VALUE
            for (i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j] == null) {
                        board[i][j] = "O"
                        best = maxOf(best, minimax(board, depth + 1, false))
                        board[i][j] = null
                    }
                }
            }
            return best
        } else {
            var best = Int.MAX_VALUE
            for (i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j] == null) {
                        board[i][j] = "X"
                        best = minOf(best, minimax(board, depth + 1, true))
                        board[i][j] = null
                    }
                }
            }
            return best
        }
    }

    fun evaluatePatterns(board: Array<Array<String?>>): Int {
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == "O")
                    return 10
                if (board[i][0] == "X")
                    return -10
            }
        }
        for (i in 0..2) {
            if (board[0][i] == board[1][i] && board[1][i] == board[i][2]) {
                if (board[0][i] == "O")
                    return 10
                if (board[0][i] == "X")
                    return -10
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == "O")
                return 10
            if (board[0][0] == "X")
                return -10
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == "O") return 10
            if (board[0][2] == "X") return -10
        }
        return 0
    }
}