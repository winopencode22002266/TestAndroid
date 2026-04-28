package com.example.proyecto.data.repository

import com.example.proyecto.data.local.dao.NoteDao
import com.example.proyecto.data.local.entity.Note
import com.example.proyecto.data.security.EncryptionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepository(
    private val noteDao: NoteDao,
    private val encryptionManager: EncryptionManager
) {
    fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { notes ->
            notes.map { note ->
                note.copy(
                    title = encryptionManager.decrypt(note.title),
                    content = encryptionManager.decrypt(note.content)
                )
            }
        }
    }

    suspend fun getNoteById(id: Int): Note? {
        val note = noteDao.getNoteById(id)
        return note?.let {
            it.copy(
                title = encryptionManager.decrypt(it.title),
                content = encryptionManager.decrypt(it.content)
            )
        }
    }

    suspend fun insertNote(note: Note) {
        val encryptedNote = note.copy(
            title = encryptionManager.encrypt(note.title),
            content = encryptionManager.encrypt(note.content)
        )
        noteDao.insertNote(encryptedNote)
    }

    suspend fun updateNote(note: Note) {
        val encryptedNote = note.copy(
            title = encryptionManager.encrypt(note.title),
            content = encryptionManager.encrypt(note.content)
        )
        noteDao.updateNote(encryptedNote)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}
