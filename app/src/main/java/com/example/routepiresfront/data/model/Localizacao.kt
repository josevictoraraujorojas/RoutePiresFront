package com.example.routepiresfront.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa um ponto de geolocalização (latitude e longitude).
 */
data class Localizacao(
    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double
)
