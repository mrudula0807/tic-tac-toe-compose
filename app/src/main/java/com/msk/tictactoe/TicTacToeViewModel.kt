package com.msk.tictactoe

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TicTacToeViewModel : ViewModel() {
    var board = mutableStateOf(Array(3) { arrayOfNulls<String>(3) })
    var currentPlayer = mutableStateOf("X")
    var isGameActive = mutableStateOf(true)
    var status = mutableStateOf("You start")
    var showResetButton = mutableStateOf(false)
    var winningLine = mutableStateOf<List<Pair<Int, Int>>?>(null)

    fun resetGame() {
        board.value = Array(3) { arrayOfNulls<String>(3) }
        currentPlayer.value = "X"
        isGameActive.value = true
        status.value = "Your turn"
        showResetButton.value = false
        winningLine.value = null
    }

    fun gameLost(winLine: List<Pair<Int, Int>>?) {
        status.value = "Oops, you've lost"
        isGameActive.value = false
        showResetButton.value = true
        winningLine.value = winLine
    }

    fun gameDraw() {
        status.value = "It's a draw"
        isGameActive.value = false
        showResetButton.value = true
    }

    fun gameWon() {
        currentPlayer.value = "X"
        status.value = "Your turn"
        //enable grids and allow player one to make next move
        isGameActive.value = true
    }

    fun playMove(i: Int, j: Int, onAutoPlay: () -> Unit) {
        if (board.value[i][j] == null && isGameActive.value) {
            board.value[i][j] = currentPlayer.value
            val result = isWin(board.value, currentPlayer.value)
            if (result.first) {
                winningLine.value = result.second
                status.value = "You've won!!!"
                isGameActive.value = false
                showResetButton.value = true
            } else if (isBoardFull(board.value)) {
                status.value = "It's a draw"
                isGameActive.value = false
                showResetButton.value = true
            } else {
                currentPlayer.value = "O"
                status.value = ""
                //box disabled to prevent multiple movements by player one
                isGameActive.value = false
                onAutoPlay()
            }
        }
    }

    fun autoPlay(onPlayed: () -> Unit) {
        //use an optimized algorithm for finding the best position for the next move of player 2
        val bestPos = getBestPosition(board.value)
        bestPos?.let { (i, j) ->
            board.value[i][j] = "O"
            onPlayed()
        }
    }

    /*
    Check if the board is full; if neither of the players have won yet and the board is full,
    it's draw*/
    fun isBoardFull(board: Array<Array<String?>>): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == null) return false
            }
        }
        return true
    }

    /*
    To check if the current player has any one complete row/column/diagonal selected
    and if so return the winning coordinates
    */
    fun isWin(board: Array<Array<String?>>, player: String): Pair<Boolean, List<Pair<Int, Int>>?> {
        for (i in 0..2) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return Pair(true, listOf(Pair(i, 0), Pair(i, 1), Pair(i, 2)))
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
                return Pair(true, listOf(Pair(0, i), Pair(1, i), Pair(2, i)))
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return Pair(true, listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2)))
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return Pair(true, listOf(Pair(0, 2), Pair(1, 1), Pair(2, 0)))

        return Pair(false, null)
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