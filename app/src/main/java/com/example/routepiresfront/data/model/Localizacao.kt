package com.example.routepiresfront.data.model

import com.google.gson.annotations.SerializedName

data class Localizacao(
    @SerializedName("localizacao") val localizacao: GeoPoint,
    @SerializedName("timesTamp") val timesTamp: String? = null // ISO 8601 date string
)