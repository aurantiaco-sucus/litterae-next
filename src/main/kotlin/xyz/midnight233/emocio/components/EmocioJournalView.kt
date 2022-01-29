package xyz.midnight233.emocio.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.JournalEntry
import xyz.midnight233.emocio.stateful.JournalEntryType
import xyz.midnight233.emocio.stateful.StateType

@Composable fun BoxScope.EmocioJournalView() {
    val lazyState = rememberLazyListState()
    val journalSize by EmocioState.journalSize
    ContentPanel {
        LazyColumn(
            state = lazyState,
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(journalSize) { index ->
                EmocioJournalEntryCard(EmocioState.journal[index])
            }
            item {
                EmocioTrailingCard()
            }
        }
        LaunchedEffect(journalSize) {
            lazyState.animateScrollToItem(journalSize - 1)
        }
        Button(onClick = {
            EmocioState.journal += JournalEntry(JournalEntryType.Notification, "Notification")
            EmocioState.journal += JournalEntry(JournalEntryType.Narration, "Narration")
            EmocioState.journal += JournalEntry(JournalEntryType.Speech, "Speaker::Speech")
            EmocioState.journal += JournalEntry(JournalEntryType.Prompt, "Prompt")
            EmocioState.journal += JournalEntry(JournalEntryType.Response, "Response")
            EmocioState.journalSize.value = EmocioState.journal.size
        }) { Text("Add narration!") }
    }
}

@Composable fun EmocioJournalCard(composable: ComposableLambda) {
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        elevation = 0.dp
    ) {
        Column {
            composable()
        }
    }
}

@Composable fun EmocioTrailingCard() {
    var response by EmocioState.response
    var state by EmocioState.stateType
    when (state) {
        StateType.Choose -> {
            EmocioConfirmSkewerCard(onConfirm = {
                state = StateType.Response
            }) {
                EmocioTrailingCardTitle("Choose one")
                SingleChooser(
                    source = EmocioState.candidates.value,
                    selectionState = EmocioState.choice,
                    modifier = Modifier.padding(start = 8.dp).fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        StateType.MultiChoose -> {
            EmocioConfirmSkewerCard(onConfirm = {
                state = StateType.Response
            }) {
                EmocioTrailingCardTitle("Choose some")
                MultiChooser(
                    source = EmocioState.candidates.value,
                    selectionsState = EmocioState.choices,
                    predicate = EmocioState.choicesPredicate.value,
                    modifier = Modifier.padding(start = 8.dp).fillMaxSize()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        StateType.String -> {
            var invalid by remember { mutableStateOf(false) }
            val predicate by EmocioState.stringPredicate
            EmocioConfirmSkewerCard(onConfirm = {
                if (predicate(response)) state = StateType.Response
            }) {
                EmocioTrailingCardTitle("Response")
                OutlinedTextField(
                    value = response,
                    onValueChange = {
                        response = it
                        invalid = !predicate(response)
                    },
                    isError = invalid,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        StateType.RangedInt -> {
            var invalid by remember { mutableStateOf(false) }
            val range by EmocioState.intRange
            EmocioConfirmSkewerCard(onConfirm = {
                val int = response.toIntOrNull()
                if (int != null) {
                    state = StateType.Response
                }
            }) {
                EmocioTrailingCardTitle("Number")
                OutlinedTextField(
                    value = response,
                    onValueChange = {
                        response = it
                        invalid = response.toIntOrNull() == null || !range.contains(response.toInt())
                    },
                    isError = invalid,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        StateType.Boolean -> {
            Row(modifier = Modifier.padding(16.dp)) {
                FloatingActionButton(
                    onClick = {
                        response = "yes"
                        state = StateType.Response
                    },
                ) { Icon(Icons.Outlined.Done, "Yes") }
                Spacer(modifier = Modifier.width(32.dp))
                FloatingActionButton(
                    onClick = {
                        response = "no"
                        state = StateType.Response
                    },
                ) { Icon(Icons.Outlined.Close, "No") }
            }
        }
        StateType.Action -> {}
        StateType.Continue -> {
            FloatingActionButton(onClick = {
                state = StateType.Response
            }) { Icon(Icons.Default.ArrowForward, "Continue") }
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

@Composable fun EmocioJournalPrefixCard(prefix: ColumnLambda, content: ComposableLambda) {
    Row {
        Column(content = prefix)
        Spacer(modifier = Modifier.width(16.dp))
        EmocioJournalCard(content)
    }
}

@Composable fun EmocioJournalSkewerCard(vararg meat: Pair<ImageVector, () -> Unit>, content: ComposableLambda) {
    EmocioJournalPrefixCard(
        prefix = {
            meat.forEachIndexed { index, pair ->
                FloatingActionButton(onClick = pair.second) { Icon(pair.first, pair.first.name) }
                if (index != meat.size - 1) Spacer(modifier = Modifier.height(16.dp))
            }
        },
        content = content
    )
}

@Composable fun EmocioConfirmSkewerCard(onConfirm: () -> Unit, content: ComposableLambda) {
    EmocioJournalSkewerCard(
        Icons.Default.Done to onConfirm,
        content = content
    )
}