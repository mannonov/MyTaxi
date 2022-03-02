package uz.behadllc.mytaxi.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.behadllc.mytaxi.model.TripX
import java.lang.reflect.Type

fun tripConvertor(string: String): TripX? {
    val tripXType: Type = object : TypeToken<TripX>() {}.type
    return Gson().fromJson<TripX>(string, tripXType)
}