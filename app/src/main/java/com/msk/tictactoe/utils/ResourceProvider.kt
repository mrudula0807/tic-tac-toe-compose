package com.msk.tictactoe.utils

import android.content.Context

interface ResourceProvider {
    fun getString(resId: Int): String
    fun getString(resId: Int, arg1: String, arg2: String): String
}
class ResourceProviderImpl(context: Context): ResourceProvider {

    private val context by lazy { context }

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getString(resId: Int, arg1: String, arg2: String): String {
        return context.getString(resId, arg1, arg2)
    }
}