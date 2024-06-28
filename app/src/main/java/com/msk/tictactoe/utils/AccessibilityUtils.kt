package com.msk.tictactoe.utils

import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import com.msk.tictactoe.viewmodel.TicTacToeViewModel

@Composable
fun GameAccessibilityAnnouncer(viewModel: TicTacToeViewModel) {
    val context = LocalContext.current
    val autoPlayResult by viewModel.autoPlayResult.collectAsState()

    LaunchedEffect(autoPlayResult) {
        autoPlayResult?.let { result ->
            announceForAccessibility(context, result)
            viewModel.resetAutoPlayResult() // Reset the result to avoid repeated announcements
        }
    }
}

fun announceForAccessibility(context: Context, textContent: String) {
    val accessibilityManager = context.getSystemService<AccessibilityManager>()
    if (accessibilityManager?.isEnabled == true) {
        accessibilityManager.sendAccessibilityEvent(
            AccessibilityEvent.obtain(AccessibilityEvent.TYPE_ANNOUNCEMENT).apply {
                className = context.javaClass.name
                packageName = context.packageName
                this.text.add(textContent)
            }
        )
    }
}