package uz.behadllc.mytaxi.network.geoposition

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.behadllc.mytaxi.model.GeoResponse
import uz.behadllc.mytaxi.model.Latlng

interface GeoService {

    @GET("reverse")
    fun getGeoCodeInfo(
        @Query("access_key") access_key: String,
        @Query("query") query: Latlng,
    ): Call<GeoResponse>


}