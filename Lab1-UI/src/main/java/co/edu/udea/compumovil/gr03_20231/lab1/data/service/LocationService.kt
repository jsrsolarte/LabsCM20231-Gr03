package co.edu.udea.compumovil.gr03_20231.lab1.data.service

import co.edu.udea.compumovil.gr03_20231.lab1.data.remote.LocationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.github.cdimascio.dotenv.dotenv

class LocationService() {

    private val apiKey: String
    private val userEmail: String
    private val locationApi: LocationApi

    init {
        val env = dotenv {
            directory = "/assets"
            filename = "env"
        }
        apiKey = "n3UwWpn6cPaUTD165coVbPwIexgKP8Cav6g0W7JkyI74sigvhUxQbFoN8ulacfdwrS0"
        val baseUrl = "https://www.universal-tutorial.com/api/"
        userEmail = "juanse.ramirez91@gmail.com"
        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        locationApi = retrofit.create(LocationApi::class.java)
    }

    private suspend fun getToken(): String {
        val token = locationApi.getToken(apiKey, userEmail)
        return "Bearer ${token.authToken}"
    }

    suspend fun getAllCountries(): List<String> {
        var token = getToken()
        var countries = locationApi.getCountries(token)
        return countries.map { it.name }
    }

    suspend fun getAllStates(country: String): List<String> {
        var token = getToken()
        var states = locationApi.getStatesByCountry(token, country)
        return states.map { it.name }
    }

    suspend fun getAllCity(state: String): List<String> {
        var token = getToken()
        var states = locationApi.getCityByState(token, state)
        return states.map { it.name }
    }
}