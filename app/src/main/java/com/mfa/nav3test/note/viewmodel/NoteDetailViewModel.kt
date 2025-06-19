package com.mfa.nav3test.note.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfa.nav3test.note.Note
import com.mfa.nav3test.note.noteItems
import com.mfa.nav3test.resources.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

typealias NoteState = ResourceState<Note>

class NoteDetailViewModel : ViewModel() {

    private val _noteState = MutableStateFlow<ResourceState<Note>>(
        ResourceState.Idle
    )
    val noteState = _noteState.asStateFlow()

    fun fetchNote(
        noteId: Int
    ) {
        _noteState.update { ResourceState.Loading }
        viewModelScope.launch {
            delay(2000)

            val noteFound = noteItems.firstOrNull { it.id == noteId }

            noteFound?.let { note->
                _noteState.update {
                    ResourceState.Success(note)
                }
            } ?: run {
                _noteState.update { ResourceState.Error("Note not found") }
            }
        }
    }
}