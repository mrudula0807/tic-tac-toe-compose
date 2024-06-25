package com.msk.tictactoe

import android.app.Application
import com.msk.tictactoe.utils.ResourceProvider

class TicTacToeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ResourceProvider.init(this)
    }
}