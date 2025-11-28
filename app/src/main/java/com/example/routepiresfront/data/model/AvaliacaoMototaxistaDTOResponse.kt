package com.example.routepiresfront.data.model

import java.util.Date

data class AvaliacaoMototaxistaDTOResponse(
    val id: String,
    val data: Date,
    val nota: Int,
    val comentario: String?,
    val corrida: String,
    val avaliadorId: String,
    val avaliadoId: String
)

