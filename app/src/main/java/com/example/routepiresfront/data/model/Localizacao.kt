package com.example.routepiresfront.data.model

import com.google.gson.annotations.SerializedName

data class Localizacao(
    @SerializedName("localizacao") val localizacao: GeoPoint,
    // Note: backend Java field is timesTamp (typo). We'll send as "timesTamp" to match model.
    @SerializedName("timesTamp") val timesTamp: String? = null // ISO 8601 date string
)