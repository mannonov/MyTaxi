package uz.behadllc.mytaxi.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.behadllc.mytaxi.model.Latlng
import uz.behadllc.mytaxi.model.TripX
import java.lang.reflect.Type

@TypeConverters
class TypeConvertor {

    val tripXType: Type = object : TypeToken<List<TripX>>() {}.type
    val latLntType: Type = object : TypeToken<List<Latlng>>() {}.type

    @TypeConverter
    fun tripsToGson(trips: List<TripX>): String {
        return Gson().toJson(trips, tripXType)
    }

    @TypeConverter
    fun gsonToTrips(string: String): List<TripX> {
        return Gson().fromJson(string, tripXType)
    }

    @TypeConverter
    fun latLngToGson(latlng: List<Latlng>): String {
        return Gson().toJson(latlng, latLntType)
    }

    @TypeConverter
    fun gsonToLatLng(string: String): List<Latlng> {
        return Gson().fromJson(string, latLntType)
    }

}