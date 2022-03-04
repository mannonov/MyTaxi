package uz.behadllc.mytaxi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import uz.behadllc.mytaxi.model.GeoResponse
import uz.behadllc.mytaxi.repository.GeoRepository
import uz.behadllc.mytaxi.utils.Resource

class GeoViewModel(private val repository: GeoRepository) : ViewModel() {

    private var geoCodeLiveData = MutableLiveData<Resource<GeoResponse>>()

    fun getGeoInfo(latLng: LatLng): LiveData<Resource<GeoResponse>> {

        viewModelScope.launch {

            geoCodeLiveData.value = repository.getGeoCodeInfo(latLng).value

        }

        return geoCodeLiveData

    }


}