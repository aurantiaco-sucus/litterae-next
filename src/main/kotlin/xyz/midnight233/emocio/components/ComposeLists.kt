package xyz.midnight233.emocio.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable fun LazySingleChooser(
    source: List<String>,
    selectionState: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    var selection by selectionState
    LazyColumn(modifier = modifier) {
        items(source.size) {
            ListItem(
                trailing = {
                    RadioButton(
                        selected = selection == it,
                        onClick = {
                            if (selection != it) selection = it
                        }
                    )
                }
            ) {
                Text(source[it])
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable fun SingleChooser(
    source: List<String>,
    selectionState: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    var selection by selectionState
    Column(modifier = modifier) {
        source.indices.forEach {
            ListItem(
                trailing = {
                    RadioButton(
                        selected = selection == it,
                        onClick = {
                            if (selection != it) selection = it
                        }
                    )
                }
            ) {
                Text(source[it])
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable fun LazyMultiChooser(
    source: List<String>,
    selectionsState: MutableState<List<Int>>,
    modifier: Modifier = Modifier
) {
    var selections by selectionsState
    LazyColumn(modifier = modifier) {
        items(source.size) {
            ListItem(
                trailing = {
                    Checkbox(
                        checked = selections.contains(it),
                        onCheckedChange = { current ->
                            selections = if (!current) selections + it else selections - it
                        }
                    )
                }
            ) {
                Text(source[it])
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable fun LazyMultiChooser(
    source: List<String>,
    selectionsState: MutableState<List<Int>>,
    predicate: (List<Int>) -> Boolean,
    modifier: Modifier = Modifier
) {
    var selections by selectionsState
    LazyColumn(modifier = modifier) {
        items(source.size) {
            ListItem(
                trailing = {
                    Checkbox(
                        checked = selections.contains(it),
                        onCheckedChange = { current ->
                            val updated = if (current) selections + it else selections - it
                            if (predicate(updated)) selections = updated
                        }
                    )
                }
            ) {
                Text(source[it])
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable fun MultiChooser(
    source: List<String>,
    selectionsState: MutableState<List<Int>>,
    predicate: (List<Int>) -> Boolean,
    modifier: Modifier = Modifier
) {
    var selections by selectionsState
    Column(modifier = modifier) {
        source.indices.forEach {
            ListItem(
                trailing = {
                    Checkbox(
                        checked = selections.contains(it),
                        onCheckedChange = { current ->
                            val updated = if (current) selections + it else selections - it
                            if (predicate(updated)) selections = updated
                        }
                    )
                }
            ) {
                Text(source[it])
            }
        }
    }
}