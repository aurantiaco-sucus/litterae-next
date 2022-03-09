package xyz.midnight233.emocio.components.segment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.midnight233.emocio.components.*
import xyz.midnight233.emocio.components.compose.ActionButton
import xyz.midnight233.emocio.components.compose.TextInput
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType

@Composable fun BoxScope.EmocioJournalView() {
    val lazyState = rememberLazyListState()
    val journalSize by EmocioState.journalSize
    ContentPanel {
        LazyColumn(
            state = lazyState,
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(journalSize) { index ->
                EmocioJournalEntry(EmocioState.journal[index])
            }
            item {
                EmocioTrailing()
            }
        }
        LaunchedEffect(journalSize) {
           if (journalSize > 0) lazyState.animateScrollToItem(journalSize - 1)
        }
    }
}

@Composable fun EmocioTrailing() {
    var response by EmocioState.stringResponse
    var state by EmocioState.stateType
    when (state) {
        StateType.Choose -> {
            EmocioConfirmSkewer(onConfirm = {
                state = StateType.Response
            }) {
                SingleChooserList(
                    source = EmocioState.choiceCandidates.value,
                    selectionState = EmocioState.choice,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        StateType.MultiChoose -> {
            EmocioConfirmSkewer(onConfirm = {
                if (EmocioState.choicesPredicate.value(EmocioState.multiChoice.value)) {
                    state = StateType.Response
                }
            }) {
                MultiChooserList(
                    source = EmocioState.choiceCandidates.value,
                    selectionsState = EmocioState.multiChoice,
                    modifier = Modifier.padding(start = 8.dp).fillMaxSize()
                )
            }
        }
        StateType.String -> {
            var invalid by remember { mutableStateOf(false) }
            val predicate by EmocioState.stringPredicate
            EmocioConfirmSkewer(onConfirm = {
                if (predicate(response)) state = StateType.Response
            }) {
                Spacer(Modifier.height(4.dp))
                TextInput(
                    value = response,
                    onValueChange = {
                        response = it
                        invalid = !predicate(response)
                    },
                    isError = invalid
                )
            }
        }
        StateType.RangedInt -> {
            var invalid by remember { mutableStateOf(false) }
            val range by EmocioState.intRange
            EmocioConfirmSkewer(onConfirm = {
                val int = response.toIntOrNull()
                if (int != null) {
                    state = StateType.Response
                }
            }) {
                Spacer(Modifier.height(4.dp))
                TextInput(
                    value = response,
                    onValueChange = {
                        response = it
                        invalid = response.toIntOrNull() == null || !range.contains(response.toInt())
                    },
                    isError = invalid
                )
            }
        }
        StateType.Boolean -> {
            Row(modifier = Modifier.padding(16.dp)) {
                ActionButton(
                    icon = Icons.Default.Done,
                    contentDescription = "Yes",
                ) {
                    response = "yes"
                    state = StateType.Response
                }
                Spacer(modifier = Modifier.width(32.dp))
                ActionButton(
                    icon = Icons.Default.Close,
                    contentDescription = "No",
                ) {
                    response = "no"
                    state = StateType.Response
                }
            }
        }
        StateType.Action -> {}
        StateType.Continue -> {
            ActionButton(
                icon = Icons.Default.ArrowForward,
                contentDescription = "Continue",
            ) {
                state = StateType.Response
            }
        }
        StateType.Response -> {
            CircularProgressIndicator()
        }
    }
}

@Composable fun EmocioTrailingCardTitle(title: String) {
    Box(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable fun EmocioSkewer(vararg meat: Pair<ImageVector, () -> Unit>, content: ColumnLambda) {
    Row {
        Column {
            meat.forEachIndexed { index, pair ->
                FloatingActionButton(
                    onClick = pair.second,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp, 0.dp, 4.dp, 6.dp),
                    backgroundColor = Color.White,
                    contentColor = Color.Black,
                ) { Icon(pair.first, pair.first.name) }
                if (index != meat.size - 1) Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column(content = content)
    }
}

@Composable fun EmocioConfirmSkewer(onConfirm: () -> Unit, content: ColumnLambda) {
    EmocioSkewer(
        Icons.Default.Done to onConfirm,
        content = content
    )
}