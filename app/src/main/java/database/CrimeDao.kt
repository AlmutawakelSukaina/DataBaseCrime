package database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.criminalintent.Crime

import java.util.*

@Dao

interface CrimeDao{

@Query("select * from crime")

fun getCrime(): LiveData<List<Crime>>

    @Query("select * from crime where id=(:id)")
 fun getCrime(id: UUID):LiveData<Crime?>
}