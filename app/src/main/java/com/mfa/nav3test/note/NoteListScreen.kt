package com.mfa.nav3test.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun NoteListScreen(
    onNoteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var sampleNotesItems by rememberSaveable { mutableStateOf<List<Note>?>(null) }

    LaunchedEffect(Unit) {
        delay(2000)
        sampleNotesItems = noteItems
    }

    sampleNotesItems?.let {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(sampleNotesItems.orEmpty()) { note ->
                NoteItemComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onNoteClick(note.id)
                        },
                    note = note
                )
            }
        }
    } ?: LoaderScreen()
}

@Composable
fun NoteItemComponent(modifier: Modifier = Modifier, note: Note) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(size = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = note.color
        )
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = note.title,
                fontSize = 18.sp
            )
            HorizontalDivider()
            Text(
                text = note.content,
            )
        }
    }
}

@Composable
fun LoaderScreen() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Loading...")
    }
}

@Preview
@Composable
fun NoteListScreenPreview() {
    NoteListScreen(onNoteClick = {})
}

@Preview
@Composable
fun NoteItemComponentPreview(modifier: Modifier = Modifier) {
    NoteItemComponent(
        modifier = modifier,
        note = noteItems.first()
    )
}