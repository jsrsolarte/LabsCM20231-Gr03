package co.edu.udea.compumovil.gr03_20231.lab1.domain

import com.google.gson.annotations.SerializedName

data class State (
    @SerializedName("state_name") val name: String
)