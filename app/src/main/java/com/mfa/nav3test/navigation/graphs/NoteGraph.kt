package com.mfa.nav3test.navigation.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.mfa.nav3test.navigation.Destination
import com.mfa.nav3test.navigation.scenes.TwoPaneScene
import com.mfa.nav3test.navigation.scenes.TwoPaneSceneStrategy
import com.mfa.nav3test.note.NoteDetailScreen
import com.mfa.nav3test.note.NoteListScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteGraph(
) {
    val backStack = rememberNavBackStack<Destination>(Destination.NoteListScreen)
    val stateHolder = rememberSaveableStateHolder()

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        sceneStrategy = TwoPaneSceneStrategy(),
        entryProvider = entryProvider {
            entry<Destination.NoteListScreen>(
                metadata = TwoPaneScene.twoPane()
            ) {
                stateHolder.SaveableStateProvider(it.title) {
                    NoteListScreen(
                        onNoteClick = { noteId ->
                            if (backStack.lastOrNull() is Destination.NoteDetailScreen) {
                                backStack.removeLastOrNull()
                            }
                            backStack.add(Destination.NoteDetailScreen(noteId))
                        }
                    )
                }
            }

            entry<Destination.NoteDetailScreen>(
                metadata = TwoPaneScene.twoPane()
            ) { entry ->
                stateHolder.SaveableStateProvider(entry.id) {
                    NoteDetailScreen(
                        viewModel = koinViewModel(),
                        noteId = entry.id
                    )
                }
            }
        }
    )
}
