package com.androidatlas.notes.core.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AtlasSyncIndicator(
    pendingCount: Int,
    isSyncing: Boolean,
    modifier: Modifier = Modifier
) {
    if (pendingCount == 0 && !isSyncing) {
        return // Don't show if nothing to sync
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (isSyncing) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondary
        },
        label = "syncIndicatorColor"
    )

    Row(
        modifier = modifier
            .background(
                color = backgroundColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSyncing) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        val text = when {
            isSyncing -> "Syncing..."
            pendingCount > 0 -> "$pendingCount change${if (pendingCount > 1) "s" else ""} pending"
            else -> "All saved"
        }

        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}