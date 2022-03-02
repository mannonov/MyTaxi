package uz.behadllc.mytaxi.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uz.behadllc.mytaxi.database.TripDatabase
import uz.behadllc.mytaxi.network.ApiClient
import uz.behadllc.mytaxi.network.TripService
import uz.behadllc.mytaxi.repository.TripRepository
import uz.behadllc.mytaxi.utils.NetworkHelper
import uz.behadllc.mytaxi.viewmodel.TripsViewModel

val appModule = module {

    viewModel<TripsViewModel> {
        TripsViewModel(get())
    }

    single<TripRepository> {
        TripRepository(remoteDataSource = get(), localDataSource = get(), networkHelper = get())
    }

    single<TripService> {
        ApiClient.getTripService
    }

    single<TripDatabase> {
        TripDatabase.getInstance(context = get())
    }

    factory<NetworkHelper> {
        NetworkHelper(context = get())
    }

}