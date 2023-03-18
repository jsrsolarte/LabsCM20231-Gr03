package co.edu.udea.compumovil.gr03_20231.lab1.data.service

import co.edu.udea.compumovil.gr03_20231.lab1.data.remote.LocationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocationService() {

    private val apiKey: String
    private val baseUrl: String
    private val retrofit: Retrofit

    init {
        val dotenv = DotEnv.load()
        apiKey = dotenv["API_KEY"]
        baseUrl = dotenv["BASE_URL"]
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        countryApi = retrofit.create(CountryApi::class.java)
    }

    suspend fun GetToken(): String {
        val token = locationApi.GetToken()
    }
}