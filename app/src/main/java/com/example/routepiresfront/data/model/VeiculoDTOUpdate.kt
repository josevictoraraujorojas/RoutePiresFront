package com.example.routepiresfront.data.model

data class VeiculoDTOUpdate(
    val placa: String,
    val modelo: String,
    val renavam: String,
    val ano: Int,
    val capacidade: Int,
    val fotoUrl: String
)