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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.midnight233.emocio.components.compose.LeftPanel
import xyz.midnight233.emocio.components.compose.EmocioNarrative
import xyz.midnight233.emocio.components.compose.EmocioNormalPadding
import xyz.midnight233.emocio.components.compose.EnumChooserButton
import xyz.midnight233.emocio.components.compose.defaultColors
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType
import litterae.content.NoteCategory
import litterae.runtime.Instance
import litterae.runtime.NoteData

@Composable fun BoxScope.EmocioNotebookView() {
    val categoryState = remember { mutableStateOf(NoteCategory.Storyline) }
    val category by categoryState
    var current by remember { mutableStateOf(emptyList<NoteData>()) }
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
    LaunchedEffect(EmocioState.notebookSize.value, category) {
        if (EmocioState.gameReady.value) current = Instance.current.notes.filter { it.category == category }
    }
}

@Composable fun EmocioNotebookCard(note: NoteData) {
    Card(
        border = BorderStroke(width = 1.dp, color = defaultColors.stroke),
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.EmocioNormalPadding(4.dp)
        ) {
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(8.dp))
            EmocioNarrative(note.content)
        }
    }
}