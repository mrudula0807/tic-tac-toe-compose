package com.msk.tictactoe.utils

import android.content.Context

object ResourceProvider {
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun getString(resId: Int, arg1: String, arg2: String): String {
        return context.getString(resId, arg1, arg2)
    }
}