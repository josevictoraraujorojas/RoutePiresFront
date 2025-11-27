package com.example.routepiresfront.data.remote.responses

import com.example.routepiresfront.data.remote.responses.VeiculoResponse
import com.google.gson.annotations.SerializedName

data class MototaxistaResponse(
    val id: String?,
    val nome: String?,
    val email: String?,
    val telefone: String?,
    val veiculo: VeiculoResponse?
)