package uz.behadllc.mytaxi.network.trips

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TripClient {

    private const val BASE_URL = "https://621e0195849220b1fc8c4ce9.mockapi.io/api/v1/"

    private fun createRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getTripService: TripService = createRetrofit().create(TripService::class.java)


}