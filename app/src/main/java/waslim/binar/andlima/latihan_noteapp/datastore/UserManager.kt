package waslim.binar.andlima.latihan_noteapp.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager (context: Context) {

    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "data_user")

    companion object {
        val ID = preferencesKey<String>("ID")
        val USERNAME = preferencesKey<String>("USERNAME")
        val PASSWORD = preferencesKey<String>("PASSWORD")
        val NAME = preferencesKey<String>("NAME")
        val BOOLEAN = preferencesKey<Boolean>("BOOLEAN")
    }

    suspend fun saveData(id: String, username: String, password: String, name: String){
        dataStore.edit {
            it[ID] = id
            it[USERNAME] = username
            it[PASSWORD] = password
            it[NAME] = name
        }
    }


    suspend fun checkData(boolean : Boolean){
        dataStore.edit {
            it[BOOLEAN] = boolean
        }
    }

    suspend fun logout(){
        dataStore.edit {
            it.clear()
        }
    }


    val id : Flow<String> = dataStore.data.map {
        it[ID] ?: ""
    }

    val username : Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    val password : Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    val name : Flow<String> = dataStore.data.map {
        it[NAME] ?: ""
    }

    val boolean : Flow<Boolean> = dataStore.data.map {
        it[BOOLEAN] ?: false
    }


}