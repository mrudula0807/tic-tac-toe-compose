package com.msk.tictactoe

import android.os.Bundle
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
        val board by remember { mutableStateOf(Array(3) { arrayOfNulls<String>(3) }) }
        var currentPlayer by remember { mutableStateOf("X") }
        var isGameActive by remember { mutableStateOf(true) }
        var status by remember { mutableStateOf("Player X's turn") }

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
                                            status = "Player $currentPlayer wins!"
                                            isGameActive = false
                                        } else if (isBoardFull(board)) {
                                            status = getString(R.string.game_status_draw)
                                            isGameActive = false
                                        } else {
                                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                                            status = "Player $currentPlayer's turn"
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
}