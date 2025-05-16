package com.example.bsef22roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    
    @Query("SELECT * FROM notes ORDER BY id DESC LIMIT 1")
    suspend fun getLatestNote(): Note?
}