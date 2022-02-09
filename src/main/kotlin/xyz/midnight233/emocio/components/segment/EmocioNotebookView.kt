package xyz.midnight233.emocio.components.segment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.midnight233.emocio.components.LeftPanel
import xyz.midnight233.emocio.components.compose.EnumChooserButton
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType
import xyz.midnight233.litterae.content.Note
import xyz.midnight233.litterae.content.NoteCategory

@Composable fun BoxScope.EmocioNotebookView() {
    val categoryState = remember { mutableStateOf(NoteCategory.Storyline) }
    val category by categoryState
    var current by remember { mutableStateOf(emptyList<Note>()) }
    LeftPanel(
        available = EmocioState.stateType.value == StateType.Action,
        header = {
            EnumChooserButton(categoryState)
        }
    ) {
        LazyColumn {
            items(current) {
                EmocioNotebookCard(it)
            }
        }
    }
    LaunchedEffect(EmocioState.notebook.value) {
        val filtered = EmocioState.notebook.value.filter { it.category == category }
        if (filtered != current) current = filtered
    }
}

@Composable fun EmocioNotebookCard(note: Note) {
    Card(
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.emocioTextPadding(4.dp)
        ) {
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(8.dp))
            EmocioContentText(note.content)
        }
    }
}