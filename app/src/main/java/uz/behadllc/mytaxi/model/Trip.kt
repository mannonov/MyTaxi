package uz.behadllc.mytaxi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip_table")
data class Trip(
    val date: String,
    @PrimaryKey
    val id: String,
    val trips: List<TripX>,
)