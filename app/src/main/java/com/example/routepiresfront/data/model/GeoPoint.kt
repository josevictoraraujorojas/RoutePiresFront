package com.example.routepiresfront.data.model

import com.google.gson.annotations.SerializedName

data class GeoPoint(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)