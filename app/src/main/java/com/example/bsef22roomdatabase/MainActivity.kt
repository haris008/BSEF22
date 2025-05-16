package com.example.bsef22roomdatabase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var noteDao: NoteDao

    private lateinit var tvStoredNote: TextView
    private lateinit var etNewNote: EditText
    private lateinit var btnSaveNote: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        tvStoredNote = findViewById(R.id.tvStoredNote)
        etNewNote = findViewById(R.id.etNewNote)
        btnSaveNote = findViewById(R.id.btnSaveNote)

        // Initialize database
        database = AppDatabase.getDatabase(this)
        noteDao = database.noteDao()

        // Load saved note when app starts
        loadSavedNote()

        // Set up save button
        btnSaveNote.setOnClickListener {
            val noteContent = etNewNote.text.toString().trim()

            if (noteContent.isNotEmpty()) {
                saveNote(noteContent)
            } else {
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadSavedNote() {
        // Use a background thread to get the note from the database
        CoroutineScope(Dispatchers.IO).launch {
            val note = noteDao.getLatestNote()

            // Update UI on the main thread
            withContext(Dispatchers.Main) {
                if (note != null) {
                    tvStoredNote.text = "Saved Note: ${note.content}"
                } else {
                    tvStoredNote.text = "No note saved yet"
                }
            }
        }
    }

    private fun saveNote(content: String) {
        // Create a new note and save it to the database
        val newNote = Note(content = content)

        // Save on a background thread
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.insertNote(newNote)

            // Update UI on the main thread
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "Note saved successfully!",
                    Toast.LENGTH_SHORT
                ).show()

                // Clear input field
                etNewNote.text.clear()

                // Reload the saved note to display it
                loadSavedNote()
            }
        }
    }
}