package com.msk.tictactoe

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TicTacToeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val soundManager = SoundManagerImpl(context.applicationContext)
        return TicTacToeViewModel(soundManager) as T
    }
}