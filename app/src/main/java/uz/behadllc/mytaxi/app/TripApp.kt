package uz.behadllc.mytaxi.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uz.behadllc.mytaxi.di.appModule

class TripApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TripApp)
            modules(listOf(appModule))
        }
    }

}