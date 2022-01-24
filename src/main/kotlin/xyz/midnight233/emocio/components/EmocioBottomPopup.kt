package xyz.midnight233.emocio.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType

val emocioInteractiveStates = setOf(
    StateType.Boolean, StateType.String, StateType.Choose, StateType.MultiChoose, StateType.RangedInt)

@OptIn(ExperimentalMaterialApi::class)
@Composable fun BoxScope.EmocioBottomPopup() {
    var state by EmocioState.stateType
    var response by EmocioState.response
    BottomPopupPanel (emocioInteractiveStates.contains(state)) {
        when (state) {
            StateType.Choose -> {
                EmocioBottomPopupConfirmableTitle("Choose one...") {
                    state = StateType.Response
                }
                LazySingleChooser(
                    source = EmocioState.candidates.value,
                    selectionState = EmocioState.choice,
                    modifier = Modifier.fillMaxSize()
                )
            }
            StateType.MultiChoose -> {
                EmocioBottomPopupConfirmableTitle("Choose one or more...") {
                    state = StateType.Response
                }
                LazyMultiChooser(
                    source = EmocioState.candidates.value,
                    selectionsState = EmocioState.choices,
                    predicate = EmocioState.choicesPredicate.value,
                    modifier = Modifier.fillMaxSize()
                )
            }
            StateType.String -> {
                var invalid by remember { mutableStateOf(false) }
                val predicate by EmocioState.stringPredicate
                EmocioBottomPopupConfirmableTitle("Give response...") {
                    if (predicate(response)) state = StateType.Response
                }
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
            }
            StateType.RangedInt -> {
                var invalid by remember { mutableStateOf(false) }
                val range by EmocioState.intRange
                EmocioBottomPopupConfirmableTitle("Give number (${range.first} ~ ${range.last})...") {
                    val int = response.toIntOrNull()
                    if (int != null) {
                        state = StateType.Response
                    }
                }
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
            }
            StateType.Boolean -> {
                EmocioBottomPopupTitle("Confirm...")
                Box(modifier = Modifier.padding(16.dp).fillMaxSize()) {
                    FloatingActionButton(
                        onClick = {
                            response = "yes"
                            state = StateType.Response
                        },
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) { Icon(Icons.Outlined.Done, "Yes") }
                    FloatingActionButton(
                        onClick = {
                            response = "no"
                            state = StateType.Response
                        },
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) { Icon(Icons.Outlined.Close, "No") }
                }
            }
            else -> {}
        }
    }
}

@Composable fun ColumnScope.EmocioBottomPopupTitle(title: String) {
    Box(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable fun ColumnScope.EmocioBottomPopupConfirmableTitle(title: String, onConfirm: () -> Unit) {
    Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).fillMaxWidth()) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Center)
        )
        Button(
            shape = RoundedCornerShape(100),
            onClick = onConfirm,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) { Text("OK") }
    }
}