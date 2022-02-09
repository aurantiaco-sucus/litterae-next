package xyz.midnight233.emocio.components.segment

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.midnight233.emocio.stateful.JournalEntry
import xyz.midnight233.emocio.stateful.JournalEntryType

@Composable fun EmocioJournalEntryCard(entry: JournalEntry) {
    when (entry.type) {
        JournalEntryType.Notification -> {
            EmocioJournalCard {
                Text(
                    text = entry.content,
                    modifier = Modifier.emocioTextPadding()
                )
            }
        }
        JournalEntryType.Narration -> {
            EmocioJournalCard {
                EmocioContentText(
                    text = entry.content,
                    modifier = Modifier.emocioTextPadding(2.dp)
                )
            }
        }
        JournalEntryType.Speech -> {
            val split = entry.content.split("::")
            EmocioJournalPrefixCard(
                prefix = {
                    EmocioSubjectChip(split[0])
                },
                content = {
                    EmocioJournalCard {
                        EmocioContentText(
                            text = split[1],
                            modifier = Modifier.emocioTextPadding()
                        )
                    }
                }
            )
        }
        JournalEntryType.Prompt -> {
            EmocioJournalPrefixCard(
                prefix = {
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(Icons.Default.ArrowBack, "Prompt")
                },
                content = {
                    EmocioJournalCard {
                        EmocioContentText(
                            text = entry.content,
                            modifier = Modifier.emocioTextPadding()
                        )
                    }
                }
            )
        }
        JournalEntryType.Response -> {
            EmocioJournalPrefixCard(
                prefix = {
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(Icons.Default.ArrowForward, "Response")
                },
                content = {
                    EmocioJournalCard {
                        EmocioContentText(
                            text = entry.content,
                            modifier = Modifier.emocioTextPadding()
                        )
                    }
                }
            )
        }
    }
}

@Composable fun Modifier.emocioTextPadding(extra: Dp = 0.dp): Modifier = this.padding(
    top = 4.dp + extra,
    bottom = 6.dp + extra,
    start = 12.dp + extra,
    end = 12.dp + extra
)

@Composable fun EmocioContentText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Serif,
        letterSpacing = 1.sp,
        modifier = modifier
    )
}

@Composable fun EmocioSubjectChip(text: String) {
    Card(
        shape = RoundedCornerShape(100),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp,
    ) {
        EmocioContentText(text, Modifier.emocioTextPadding())
    }
}