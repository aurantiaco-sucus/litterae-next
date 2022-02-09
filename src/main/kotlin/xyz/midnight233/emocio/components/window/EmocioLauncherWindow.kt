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
    var instances by remember { mutableStateOf(emptyList<Instance>()) }
    var instanceIndex by remember { mutableStateOf(0) }
    Box(Modifier.fillMaxSize()) {
        Column(Modifier
            .padding(top = 8.dp)
            .fillMaxSize()
        ) {
            Column(Modifier.padding(start = 16.dp)) {
                Text(
                    text = "Artifact",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Row {
                    Button(
                        shape = RoundedCornerShape(20.dp),
                        onClick = {
                            artifactChooserOpened = !artifactChooserOpened
                        }
                    ) { Text(artifact.title) }
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
                Text(
                    text = "Instance",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Button(
                    shape = RoundedCornerShape(100),
                    onClick = {
                        val name = JOptionPane.showInputDialog("Name of instance?")?: ""
                        if (name != "" && EmocioBackend.checkInstanceName(name))
                            instances += EmocioBackend.instanceNamed(name)
                    }
                ) {
                    Text("Create")
                }
            }
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(instances.size) {
                    ListItem(
                        trailing = {
                            if (instanceIndex == it) Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Selected"
                            )
                        },
                        modifier = Modifier
                            .clickable {
                                instanceIndex = it
                            }
                    ) {
                        Text(instances[it].instanceName)
                    }
                }
            }
        }
        if (instances.isNotEmpty()) FloatingActionButton(
            onClick = {
                Frontend.current = EmocioFrontend
                Instance.current = instances[instanceIndex]
                EmocioState.gameReady.value = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.ArrowForward, "Start game")
        }
    }
}