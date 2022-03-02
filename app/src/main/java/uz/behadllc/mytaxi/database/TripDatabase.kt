package uz.behadllc.mytaxi.database

import android.content.Context
import androidx.room.*
import uz.behadllc.mytaxi.model.Trip
import uz.behadllc.mytaxi.utils.TypeConvertor

@Database(entities = [Trip::class],
    exportSchema = false,
    version = 1)
@TypeConverters(TypeConvertor::class)
abstract class TripDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        var INSTANCE: TripDatabase? = null
        fun getInstance(context: Context): TripDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TripDatabase::class.java,
                        "trip-database"
                    ).build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}