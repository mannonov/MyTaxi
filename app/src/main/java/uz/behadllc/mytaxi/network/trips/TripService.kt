package uz.behadllc.mytaxi.network.trips

import retrofit2.Response
import retrofit2.http.GET
import uz.behadllc.mytaxi.model.Trip

interface TripService {

    @GET("mocktrips")
    suspend fun getAllTrips(): Response<List<Trip>>

}