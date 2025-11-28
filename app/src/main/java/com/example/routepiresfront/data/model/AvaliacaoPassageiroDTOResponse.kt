package com.example.routepiresfront.data.model

import java.util.Date

data class AvaliacaoPassageiroDTOResponse(
    val id: String,
    val data: Date,
    val nota: Int,
    val comentario: String?,
    val corrida: String,
    val avaliador: String,
    val avaliado: String
)

