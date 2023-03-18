package co.edu.udea.compumovil.gr03_20231.lab1.data.remote

import co.edu.udea.compumovil.gr03_20231.lab1.domain.ApiAuth
import retrofit2.http.GET
import retrofit2.http.Header

interface LocationApi {

    @GET("getaccesstoken")
    suspend fun GetToken(@Header("api-token") apiToken: String ): ApiAuth
}