package com.example.proyecto.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.data.local.entity.Note
import com.example.proyecto.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NoteUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false
)

data class AddEditNoteUiState(
    val noteId: Int? = null,
    val title: String = "",
    val content: String = ""
)

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    private val _addEditUiState = MutableStateFlow(AddEditNoteUiState())
    val addEditUiState: StateFlow<AddEditNoteUiState> = _addEditUiState.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getAllNotes().collect { notes ->
                _uiState.update { it.copy(notes = notes, isLoading = false) }
            }
        }
    }

    fun loadNoteForEditing(id: Int) {
        viewModelScope.launch {
            val note = repository.getNoteById(id)
            note?.let {
                _addEditUiState.update { state ->
                    state.copy(
                        noteId = it.id,
                        title = it.title,
                        content = it.content
                    )
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _addEditUiState.update { it.copy(title = title) }
    }

    fun updateContent(content: String) {
        _addEditUiState.update { it.copy(content = content) }
    }

    fun saveNote() {
        viewModelScope.launch {
            val state = _addEditUiState.value
            val note = Note(
                id = state.noteId ?: 0,
                title = state.title,
                content = state.content
            )
            if (state.noteId == null) {
                repository.insertNote(note)
            } else {
                repository.updateNote(note)
            }
            _addEditUiState.update { AddEditNoteUiState() }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun resetAddEditState() {
        _addEditUiState.update { AddEditNoteUiState() }
    }
}
