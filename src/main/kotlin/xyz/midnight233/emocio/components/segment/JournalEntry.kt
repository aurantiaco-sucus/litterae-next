package xyz.midnight233.emocio.components.segment

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.midnight233.emocio.components.compose.EmocioEmphasize
import xyz.midnight233.emocio.components.compose.EmocioNarrative
import xyz.midnight233.emocio.components.compose.EmocioNormalPadding
import xyz.midnight233.emocio.stateful.JournalEntry
import xyz.midnight233.emocio.stateful.JournalEntryType

@Composable fun EmocioJournalEntry(entry: JournalEntry) {
    when (entry.type) {
        JournalEntryType.Notification -> {
            Text(
                text = entry.content,
                modifier = Modifier.EmocioNormalPadding()
            )
        }
        JournalEntryType.Narration -> {
            EmocioNarrative(
                text = entry.content,
                modifier = Modifier.EmocioNormalPadding()
            )
        }
        JournalEntryType.Speech -> {
            val split = entry.content.split("::")
            Row {
                EmocioEmphasize(split[0], Modifier.EmocioNormalPadding())
                EmocioNarrative(
                    text = split[1],
                    modifier = Modifier.EmocioNormalPadding()
                )
            }
        }
        JournalEntryType.Prompt -> {
            Row {
                Column(Modifier.padding(top = 4.dp, end = 4.dp)) {
                    Icon(Icons.Default.ArrowBack, "Prompt")
                }
                Spacer(Modifier.width(4.dp))
                EmocioNarrative(
                    text = entry.content,
                    modifier = Modifier.EmocioNormalPadding()
                )
            }
        }
        JournalEntryType.Response -> {
            Row {
                Column(Modifier.padding(top = 4.dp, end = 4.dp)) {
                    Icon(Icons.Default.ArrowForward, "Response")
                }
                Spacer(Modifier.width(4.dp))
                EmocioNarrative(
                    text = entry.content,
                    modifier = Modifier.EmocioNormalPadding()
                )
            }
        }
    }
}