package com.msk.tictactoe.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msk.tictactoe.utils.ResourceProviderImpl
import com.msk.tictactoe.utils.SoundManagerImpl

class TicTacToeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val soundManager = SoundManagerImpl(context.applicationContext)
        val resourcesProvider = ResourceProviderImpl(context.applicationContext)
        return TicTacToeViewModel(soundManager, resourcesProvider) as T
    }
}