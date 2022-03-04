package uz.behadllc.mytaxi.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uz.behadllc.mytaxi.database.TripDatabase
import uz.behadllc.mytaxi.network.geoposition.GeoClient
import uz.behadllc.mytaxi.network.geoposition.GeoService
import uz.behadllc.mytaxi.network.trips.TripClient
import uz.behadllc.mytaxi.network.trips.TripService
import uz.behadllc.mytaxi.repository.GeoRepository
import uz.behadllc.mytaxi.repository.TripRepository
import uz.behadllc.mytaxi.utils.NetworkHelper
import uz.behadllc.mytaxi.viewmodel.GeoViewModel
import uz.behadllc.mytaxi.viewmodel.TripsViewModel
import kotlin.math.sin

val appModule = module {

    viewModel<TripsViewModel> {
        TripsViewModel(tripRepository = get())
    }

    viewModel<GeoViewModel>{
        GeoViewModel(repository = get())
    }

    factory<TripRepository> {
        TripRepository(remoteDataSource = get(), localDataSource = get(), networkHelper = get())
    }

    factory<GeoRepository> {
        GeoRepository(remoteDataSource = get(),networkHelper = get())
    }

    factory<GeoService> {
        GeoClient.geoService
    }

    factory<TripService> {
        TripClient.getTripService
    }

    single<TripDatabase> {
        TripDatabase.getInstance(context = get())
    }

    factory<NetworkHelper> {
        NetworkHelper(context = get())
    }

}