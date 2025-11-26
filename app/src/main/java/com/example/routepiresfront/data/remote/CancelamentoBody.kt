package com.example.routepiresfront.data.remote

import com.google.gson.annotations.SerializedName


data class CancelamentoBody(
    @SerializedName("motivo")
    val motivo: String?
)

