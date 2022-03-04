package uz.behadllc.mytaxi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.behadllc.mytaxi.model.Trip
import uz.behadllc.mytaxi.repository.TripRepository
import uz.behadllc.mytaxi.utils.Resource

class TripsViewModel(private val tripRepository: TripRepository) : ViewModel() {

    private var trips = MutableLiveData<Resource<List<Trip>>>()


    init {
        getAllTrips()
    }

    private fun getAllTrips() {
        viewModelScope.launch {
            trips = tripRepository.getAllTrips() as MutableLiveData<Resource<List<Trip>>>
        }
    }


    fun getTrips(): LiveData<Resource<List<Trip>>> = trips

}