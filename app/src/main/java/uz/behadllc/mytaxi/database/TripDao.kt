package uz.behadllc.mytaxi.database

import androidx.room.*
import uz.behadllc.mytaxi.model.Trip

@Dao
interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTrips(trips: List<Trip>)

    @Delete
    fun deleteTrip(miniTrip: Trip)

    @Query("SELECT * FROM trip_table")
    fun getAllTrips(): List<Trip>

}