package com.msk.tictactoe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class TicTacToeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var soundManager: SoundManager
    private lateinit var viewModel: TicTacToeViewModel

    @Before
    fun setUp() {
        soundManager = mock<SoundManagerImpl>()
        viewModel = TicTacToeViewModel(soundManager)
    }

    @Test
    fun testInitialState() {
        Assert.assertEquals("X", viewModel.currentPlayer.value)
        Assert.assertEquals("Make your first move", viewModel.status.value)
        Assert.assertTrue(viewModel.isGameActive.value)
        Assert.assertFalse(viewModel.showResetButton.value)
        Assert.assertNull(viewModel.winningLine.value)
    }

    @Test
    fun testResetGame() {
        viewModel.resetGame()
        verify(soundManager).playResetSound()
        Assert.assertEquals("X", viewModel.currentPlayer.value)
        Assert.assertEquals("Make your first move", viewModel.status.value)
        Assert.assertTrue(viewModel.isGameActive.value)
        Assert.assertFalse(viewModel.showResetButton.value)
        Assert.assertNull(viewModel.winningLine.value)
    }

    @Test
    fun testGameLost() {
        val winLine = listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2))
        viewModel.gameLost(winLine)
        verify(soundManager).playLostSound()
        Assert.assertEquals("Oops, you've lost", viewModel.status.value)
        Assert.assertFalse(viewModel.isGameActive.value)
        Assert.assertTrue(viewModel.showResetButton.value)
        Assert.assertEquals(winLine, viewModel.winningLine.value)
    }

    @Test
    fun testGameDraw() {
        viewModel.gameDraw()
        verify(soundManager).playDrawSound()
        Assert.assertEquals("It's a draw", viewModel.status.value)
        Assert.assertFalse(viewModel.isGameActive.value)
        Assert.assertTrue(viewModel.showResetButton.value)
    }

    @Test
    fun testGameContinue() {
        viewModel.gameContinue()
        Assert.assertEquals("X", viewModel.currentPlayer.value)
        Assert.assertEquals("Your turn", viewModel.status.value)
        Assert.assertTrue(viewModel.isGameActive.value)
    }

    @Test
    fun testOnCleared() {
        val method = ViewModel::class.java.getDeclaredMethod("onCleared")
        method.isAccessible = true
        method.invoke(viewModel)
        verify(soundManager).release()
    }

    @Test
    fun testPlayMoveAndWinVertical() {
        val onAutoPlay: () -> Unit = { viewModel.autoPlay() }
        viewModel.playMove(0, 0, onAutoPlay)
        viewModel.playMove(1, 1, onAutoPlay)
        viewModel.playMove(2, 0, onAutoPlay)
        viewModel.playMove(1, 0, onAutoPlay)
        val result = viewModel.isWin(viewModel.board.value, viewModel.currentPlayer.value)
        if (result.first) {
            verify(soundManager).playWinSound()
            Assert.assertEquals("You've won!!!", viewModel.status.value)
            Assert.assertFalse(viewModel.isGameActive.value)
            Assert.assertTrue(viewModel.showResetButton.value)
            Assert.assertEquals(result.second, viewModel.winningLine.value)
        }
    }

    @Test
    fun testPlayMoveAndWinDiagonalRight() {
        val onAutoPlay: () -> Unit = { viewModel.autoPlay() }
        viewModel.playMove(1, 1, onAutoPlay)
        viewModel.playMove(2, 2, onAutoPlay)
        viewModel.playMove(0, 2, onAutoPlay)
        viewModel.playMove(2, 0, onAutoPlay)
        val result = viewModel.isWin(viewModel.board.value, viewModel.currentPlayer.value)
        if (result.first) {
            verify(soundManager).playWinSound()
            Assert.assertEquals("You've won!!!", viewModel.status.value)
            Assert.assertFalse(viewModel.isGameActive.value)
            Assert.assertTrue(viewModel.showResetButton.value)
            Assert.assertEquals(result.second, viewModel.winningLine.value)
        }
    }

    @Test
    fun testPlayMoveAndWinDiagonalLeft() {
        val onAutoPlay: () -> Unit = { viewModel.autoPlay() }
        viewModel.playMove(0, 0, onAutoPlay)
        viewModel.playMove(1, 2, onAutoPlay)
        viewModel.playMove(1, 1, onAutoPlay)
        viewModel.playMove(2, 2, onAutoPlay)
        val result = viewModel.isWin(viewModel.board.value, viewModel.currentPlayer.value)
        if (result.first) {
            verify(soundManager).playWinSound()
            Assert.assertEquals("You've won!!!", viewModel.status.value)
            Assert.assertFalse(viewModel.isGameActive.value)
            Assert.assertTrue(viewModel.showResetButton.value)
            Assert.assertEquals(result.second, viewModel.winningLine.value)
        }
    }

    @Test
    fun testPlayMoveAndWinHorizontal() {
        val onAutoPlay: () -> Unit = { viewModel.autoPlay() }
        viewModel.playMove(0, 2, onAutoPlay)
        viewModel.playMove(2, 2, onAutoPlay)
        viewModel.playMove(2, 0, onAutoPlay)
        viewModel.playMove(2, 1, onAutoPlay)
        val result = viewModel.isWin(viewModel.board.value, viewModel.currentPlayer.value)
        if (result.first) {
            verify(soundManager).playWinSound()
            Assert.assertEquals("You've won!!!", viewModel.status.value)
            Assert.assertFalse(viewModel.isGameActive.value)
            Assert.assertTrue(viewModel.showResetButton.value)
            Assert.assertEquals(result.second, viewModel.winningLine.value)
        }
    }

    @Test
    fun testPlayMoveAndLost() {
        val onAutoPlay: () -> Unit = { viewModel.autoPlay() }
        viewModel.playMove(0, 0, onAutoPlay)
        viewModel.playMove(1, 1, onAutoPlay)
        viewModel.playMove(2, 0, onAutoPlay)
        viewModel.playMove(2, 1, onAutoPlay)
        val result = viewModel.isWin(viewModel.board.value, viewModel.currentPlayer.value)
        if (result.first) {
            verify(soundManager).playLostSound()
            Assert.assertEquals("Oops, you've lost", viewModel.status.value)
            Assert.assertFalse(viewModel.isGameActive.value)
            Assert.assertTrue(viewModel.showResetButton.value)
            Assert.assertEquals(result.second, viewModel.winningLine.value)
        }
    }

    @Test
    fun testPlayMoveAndDraw() {
        val onAutoPlay: () -> Unit = { viewModel.autoPlay() }
        viewModel.playMove(0, 0, onAutoPlay)
        viewModel.playMove(1, 1, onAutoPlay)
        viewModel.playMove(2, 1, onAutoPlay)
        viewModel.playMove(1, 2, onAutoPlay)
        viewModel.playMove(2, 0, onAutoPlay)
        verify(soundManager).playDrawSound()
        Assert.assertEquals("It's a draw", viewModel.status.value)
        Assert.assertFalse(viewModel.isGameActive.value)
        Assert.assertTrue(viewModel.showResetButton.value)
    }

    @Test
    fun testIsBoardFull() {
        viewModel.board.value = arrayOf(
            arrayOf("X", "O", "X"),
            arrayOf("X", "O", "O"),
            arrayOf("O", "X", "X")
        )
        Assert.assertTrue(viewModel.isBoardFull(viewModel.board.value))
    }
}