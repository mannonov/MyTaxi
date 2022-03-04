package uz.behadllc.mytaxi.network.geoposition

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


object GeoClient {

    private const val BASE_URL = "http://api.positionstack.com/v1/"

    private fun createRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
            .connectionSpecs(listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val geoService: GeoService =
        createRetrofit().create(GeoService::class.java)

}