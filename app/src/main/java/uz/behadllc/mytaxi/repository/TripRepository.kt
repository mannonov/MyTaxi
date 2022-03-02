package uz.behadllc.mytaxi.repository

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.behadllc.mytaxi.database.TripDatabase
import uz.behadllc.mytaxi.model.Trip
import uz.behadllc.mytaxi.network.TripService
import uz.behadllc.mytaxi.utils.NetworkHelper
import uz.behadllc.mytaxi.utils.Resource

class TripRepository(
    private val remoteDataSource: TripService,
    private val localDataSource: TripDatabase,
    private val networkHelper: NetworkHelper,
) {

    suspend fun getAllTrips() = liveData<Resource<List<Trip>>> {

        emit(Resource.loading(null))

        withContext(Dispatchers.IO) {
            if (networkHelper.isNetWorkConnected()) {
                val trips = remoteDataSource.getAllTrips()
                if (trips.isSuccessful) {
                    emit(Resource.success(trips.body()))
                    localDataSource.tripDao().addTrips(trips.body() ?: emptyList())
                } else {
                    emit(Resource.error(trips.errorBody()?.string() ?: "Empty",
                        emptyList()))
                }
            } else {
                emit(Resource.success(localDataSource.tripDao().getAllTrips()))
            }
        }

    }

}