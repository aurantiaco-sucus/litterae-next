package xyz.midnight233.emocio.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import xyz.midnight233.emocio.components.compose.ActionButton
import xyz.midnight233.emocio.components.compose.EmocioTextButton
import xyz.midnight233.emocio.implementation.EmocioBackend
import xyz.midnight233.emocio.implementation.EmocioFrontend
import xyz.midnight233.emocio.implementation.EmocioRuntime
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.litterae.runtime.Frontend
import xyz.midnight233.litterae.runtime.Instance
import javax.swing.JOptionPane

@OptIn(ExperimentalMaterialApi::class)
@Composable fun EmocioLauncherWindow() {
    var artifactIndex by remember { mutableStateOf(0) }
    val artifact by derivedStateOf { EmocioState.emocioArtifacts[artifactIndex] }
    var artifactChooserOpened by remember { mutableStateOf(false) }
    var instances by remember { mutableStateOf(EmocioBackend.instances) }
    var instanceIndex by remember { mutableStateOf(0) }
    Box(Modifier.fillMaxSize()) {
        Column(Modifier
            .padding(top = 8.dp)
            .fillMaxSize()
        ) {
            Column(Modifier.padding(start = 16.dp)) {
                Text(
                    text = "ARTIFACT",
                    fontWeight = FontWeight.Light,
                    letterSpacing = 4.sp,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
                )
                Spacer(Modifier.height(4.dp))
                Row {
                    EmocioTextButton(
                        onClick = {
                            artifactChooserOpened = !artifactChooserOpened
                        },
                        text = artifact.title
                    )
                    DropdownMenu(
                        expanded = artifactChooserOpened,
                        onDismissRequest = {
                            artifactChooserOpened = false
                        },
                    ) {
                        EmocioState.emocioArtifacts.indices.forEach {
                            DropdownMenuItem(onClick = {
                                artifactIndex = it
                                artifactChooserOpened = false
                                EmocioRuntime.artifact = artifact
                                EmocioBackend.composeBackendInit()
                                instances = EmocioBackend.instances
                            }){
                                Text(EmocioState.emocioArtifacts[it].title)
                            }
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "INSTANCE",
                    fontWeight = FontWeight.Light,
                    letterSpacing = 4.sp,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
                )
                Spacer(Modifier.height(4.dp))
                EmocioTextButton(
                    onClick = {
                        val name = JOptionPane.showInputDialog("Name of instance?")?: ""
                        if (name != "" && EmocioBackend.checkInstanceName(name))
                            instances += EmocioBackend.instanceNamed(name)
                    },
                    text = "Create"
                )
            }
            Spacer(Modifier.height(16.dp))
            LazyColumn {
                items(instances.size) {
                    ListItem(
                        trailing = {
                            if (instanceIndex == it) Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Selected",
                                modifier = Modifier.scale(0.75f)
                            )
                        },
                        modifier = Modifier
                            .height(36.dp)
                            .clickable {
                                instanceIndex = it
                            }
                    ) {
                        Row {
                            Spacer(Modifier.width(2.dp))
                            Text(
                                text = instances[it].instanceName.uppercase().toCharArray().joinToString(" "),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light
                            )
                        }

                    }
                }
            }
        }
        if (instances.isNotEmpty()) ActionButton(
            icon = Icons.Default.ArrowForward,
            contentDescription = "Start game",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .scale(0.75f)
        ) {
            Frontend.current = EmocioFrontend
            Instance.current = instances[instanceIndex]
            EmocioState.gameReady.value = true
            Instance.instanceReady.set(true)
        }
    }
}