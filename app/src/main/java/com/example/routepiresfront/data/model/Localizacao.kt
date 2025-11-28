package com.example.routepiresfront.data.model

import com.google.gson.annotations.SerializedName

/**
 * Representa um ponto de geolocalização (latitude e longitude).
 */
data class Localizacao(
    @SerializedName("latitude")
    val latitude: Double = 0.0,

    @SerializedName("longitude")
    val longitude: Double = 0.0,

    @SerializedName("endereco")
    val endereco: String? = null,

    @SerializedName("nome")
    val nome: String? = null
)
