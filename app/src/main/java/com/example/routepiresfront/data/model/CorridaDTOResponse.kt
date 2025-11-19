package com.example.routepiresfront.data.model

data class CorridaDTOResponse(
    val id: String? = null,
    val passageiroId: String? = null,
    val mototaxistaId: String? = null,
    val dataInicio: String? = null,
    val dataFim: String? = null,
    val localPartida: String? = null,
    val localDestino: String? = null,
    val valor: Float? = null,
    val status: String? = null // "ATIVA", "FINALIZADA", "CANCELADA"
)