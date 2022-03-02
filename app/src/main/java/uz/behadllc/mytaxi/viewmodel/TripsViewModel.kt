package uz.behadllc.mytaxi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import uz.behadllc.mytaxi.model.Trip
import uz.behadllc.mytaxi.model.TripX
import uz.behadllc.mytaxi.repository.TripRepository
import uz.behadllc.mytaxi.utils.Resource

class TripsViewModel(private val tripRepository: TripRepository) : ViewModel() {

    private var trips = MutableLiveData<Resource<List<Trip>>>()
    var currentTrip = MutableLiveData<TripX>()


    init {
        getAllTrips()
        Log.d("ViewModel", "ViewModelCreate bo'ldi")
    }

    private fun getAllTrips() {
        viewModelScope.launch {
            trips = tripRepository.getAllTrips() as MutableLiveData<Resource<List<Trip>>>
        }
    }


    fun getTrips(): LiveData<Resource<List<Trip>>> = trips

}