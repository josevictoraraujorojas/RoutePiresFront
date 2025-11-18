package com.example.routepiresfront.data.model

import com.google.gson.annotations.SerializedName

data class VeiculoCreate(
    @SerializedName("placa") val placa: String,
    @SerializedName("modelo") val modelo: String,
    @SerializedName("renavam") val renavam: String,
    @SerializedName("ano") val ano: Int,
    @SerializedName("capacidade") val capacidade: Int = 1,
    @SerializedName("fotoUrl") val fotoUrl: String
)