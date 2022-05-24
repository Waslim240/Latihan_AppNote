package waslim.binar.andlima.latihan_noteapp.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User) : Long

    @Query("SELECT name FROM User WHERE User.username = :email AND User.password = :password")
    fun getPengguna(email : String, password : String) : String

}