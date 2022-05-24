package waslim.binar.andlima.latihan_noteapp.local.note

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun getAllNoteTaking() : List<Note>

    @Insert
    fun insertNoteTaking(note: Note) : Long

    @Update
    fun updateNoteTaking(note: Note) : Int

    @Delete
    fun deleteNoteTaking(note: Note) : Int
}