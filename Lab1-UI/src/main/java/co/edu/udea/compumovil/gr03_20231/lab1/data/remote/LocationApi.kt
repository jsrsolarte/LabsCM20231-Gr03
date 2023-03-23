package co.edu.udea.compumovil.gr03_20231.lab1.data.remote

import co.edu.udea.compumovil.gr03_20231.lab1.domain.ApiAuth
import co.edu.udea.compumovil.gr03_20231.lab1.domain.City
import co.edu.udea.compumovil.gr03_20231.lab1.domain.Country
import co.edu.udea.compumovil.gr03_20231.lab1.domain.State
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LocationApi {

    @GET("getaccesstoken")
    suspend fun getToken(
        @Header("api-token") apiToken: String,
        @Header("user-email") user: String
    ): ApiAuth

    @GET("countries")
    suspend fun getCountries(@Header("Authorization") token: String): List<Country>

    @GET("states/{country}")
    suspend fun getStatesByCountry(@Header("Authorization") token: String, @Path("country") country: String): List<State>

    @GET("cities/{state}")
    suspend fun getCityByState(@Header("Authorization") token: String, @Path("state") state: String): List<City>
}