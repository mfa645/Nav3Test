package com.mfa.nav3test.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.mfa.nav3test.navigation.graphs.HomeGraph
import com.mfa.nav3test.navigation.graphs.NoteGraph
import com.mfa.nav3test.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRoot() {
    var currentBottomBarScreen: Destination by rememberSaveable(stateSaver = BottomBarScreenSaver) {
        mutableStateOf(Destination.HomeGraph)
    }

    val backStack = rememberNavBackStack<Destination>(Destination.HomeGraph)
    val stateHolder = rememberSaveableStateHolder()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                currentDestination = currentBottomBarScreen,
                onDestinationClicked = { destination ->
                    if (backStack.lastOrNull() != destination) {
                        backStack.removeLastOrNull()
                        backStack.add(destination)
                        currentBottomBarScreen = destination
                    }
                }
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text((backStack.lastOrNull() as? Destination)?.title.orEmpty())
                },
                actions = {
                    IconButton(
                        onClick = {
                            backStack.add(Destination.Settings)
                        }
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        NavDisplay(
            modifier = Modifier.padding(paddingValues),
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
                currentBottomBarScreen =
                    backStack.lastOrNull() as? Destination ?: Destination.HomeGraph
            },
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<Destination.NoteGraph> {
                    stateHolder.SaveableStateProvider(Destination.NoteGraph.title) {
                        NoteGraph()
                    }
                }

                entry<Destination.HomeGraph> {
                    stateHolder.SaveableStateProvider(Destination.HomeGraph.title) {
                        HomeGraph()
                    }
                }

                entry<Destination.Settings> {
                    stateHolder.SaveableStateProvider(Destination.Settings.title) {
                        SettingsScreen()
                    }
                }
            }
        )
    }
}

@Composable
private fun BottomBar(
    currentDestination: Destination,
    onDestinationClicked: (Destination) -> Unit
) {

    NavigationBar {
        Items.forEach { item ->
            NavigationBarItem(
                selected = item.destination == currentDestination,
                onClick = {
                    onDestinationClicked(item.destination)
                },
                label = {
                    Text(item.title)
                },
                icon = {
                    Icon(item.icon, contentDescription = null)
                },
            )
        }
    }
}

private data class Item(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val destination: Destination,
)

private val Items = listOf(
    Item(
        0,
        "Home",
        Icons.Default.Home,
        Destination.HomeGraph,
    ),
    Item(
        1,
        "Notes",
        Icons.AutoMirrored.Filled.List,
        Destination.NoteGraph,
    ),
)

val BottomBarScreenSaver = Saver<Destination, String>(
    save = { it::class.simpleName ?: "Uknown" },
    restore = {
        when (it) {
            Destination.HomeGraph::class.simpleName -> Destination.HomeGraph
            Destination.NoteGraph::class.simpleName -> Destination.NoteGraph
            else -> Destination.HomeGraph
        }
    }
)
