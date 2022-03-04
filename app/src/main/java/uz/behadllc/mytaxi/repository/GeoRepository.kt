package uz.behadllc.mytaxi.repository

import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.behadllc.mytaxi.model.GeoResponse
import uz.behadllc.mytaxi.model.Latlng
import uz.behadllc.mytaxi.network.geoposition.GeoService
import uz.behadllc.mytaxi.utils.NetworkHelper
import uz.behadllc.mytaxi.utils.Resource

class GeoRepository(
    private val remoteDataSource: GeoService,
    private val networkHelper: NetworkHelper,
) {

    suspend fun getGeoCodeInfo(latLng: LatLng) = liveData<Resource<GeoResponse>> {

//        emit(Resource.loading(null))
//
//        withContext(Dispatchers.IO) {
//
//            if (networkHelper.isNetWorkConnected()) {
//
//                val geoResponse =
//                    remoteDataSource.getGeoCodeInfo(access_key = "f69e3c084c93c56331d9ac63f0df2e41",
//                        query = Latlng(latLng.latitude, latLng.longitude))
//
//                if (geoResponse.isSuccessful) {
//                    emit(Resource.success(geoResponse.body()))
//                } else {
//                    emit(Resource.error(geoResponse.errorBody()?.string() ?: "Empty", null))
//                }
//
//            }


//        }

    }

}