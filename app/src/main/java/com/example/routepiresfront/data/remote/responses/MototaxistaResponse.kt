package com.example.routepiresfront.data.remote.responses

import com.google.gson.annotations.SerializedName

data class MototaxistaResponse(
    val id: String?,
    @SerializedName("nome")
    val nomeCompleto: String?,
    val email: String?,
    val telefone: String?
)