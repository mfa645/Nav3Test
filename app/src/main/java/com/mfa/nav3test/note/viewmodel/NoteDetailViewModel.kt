package com.mfa.nav3test.note.viewmodel

import androidx.lifecycle.ViewModel
import com.mfa.nav3test.note.noteItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteDetailViewModel(
    private val noteId: Int
): ViewModel() {

    private val _noteState = MutableStateFlow(
        noteItems.first { it.id == noteId }
    )
    val noteState = _noteState.asStateFlow()
}