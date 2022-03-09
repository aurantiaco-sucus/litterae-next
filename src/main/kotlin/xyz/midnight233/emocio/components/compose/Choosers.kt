package xyz.midnight233.emocio.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.midnight233.emocio.components.compose.EmocioNarrative

@OptIn(ExperimentalMaterialApi::class)
@Composable fun SingleChooserList(
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
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Black,
                            unselectedColor = Color.LightGray,
                            disabledColor = Color.LightGray
                        )
                    )
                },
                modifier = Modifier.height(36.dp)
            ) {
                EmocioNarrative(source[it])
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable fun MultiChooserList(
    source: List<String>,
    selectionsState: MutableState<List<Int>>,
    modifier: Modifier = Modifier
) = MultiChooserList(
    source = source,
    selectionsState = selectionsState,
    predicate = { true },
    modifier = modifier
)

@OptIn(ExperimentalMaterialApi::class)
@Composable fun MultiChooserList(
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
                EmocioNarrative(source[it])
            }
        }
    }
}