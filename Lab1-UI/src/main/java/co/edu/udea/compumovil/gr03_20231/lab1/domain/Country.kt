package co.edu.udea.compumovil.gr03_20231.lab1.domain

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("country_name") val name: String,
    @SerializedName("country_short_name") var shortName: String,
    @SerializedName("country_phone_code") var phoneCode: String
)