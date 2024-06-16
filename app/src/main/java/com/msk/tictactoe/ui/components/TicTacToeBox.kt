package com.msk.tictactoe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TicTacToeBox(boardContent: String?, isGameActive: Boolean, onPlayed: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(
                MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
            .clickable(enabled = isGameActive, onClick = onPlayed),
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = boardContent ?: "",
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}