package com.mfa.nav3test.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mfa.nav3test.note.viewmodel.NoteDetailViewModel
import com.mfa.nav3test.note.viewmodel.NoteState
import com.mfa.nav3test.resources.ResourceState

@Composable
fun NoteDetailScreen(
    noteId: Int,
    viewModel: NoteDetailViewModel
) {
    val noteState by viewModel.noteState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchNote(noteId)
    }

    NoteDetailComponent(
        modifier = Modifier.fillMaxSize(),
        noteState = noteState
    )
}

@Composable
fun NoteDetailComponent(
    modifier: Modifier = Modifier,
    noteState: NoteState
) {
    when (noteState) {
        is ResourceState.Loading -> {
            LoaderScreen()
        }

        is ResourceState.Error -> {
            Text(text = noteState.exception)
        }

        is ResourceState.Success -> {
            with(noteState.data) {
                Column(
                    modifier = modifier
                        .background(color)
                        .padding(16.dp)
                ) {

                    Text(
                        text = title,
                        fontSize = 26.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = content,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NoteDetailComponentPreview() {
    NoteDetailComponent(
        modifier = Modifier.fillMaxSize(), noteState = ResourceState.Success(
            noteItems.first()
        )
    )
}