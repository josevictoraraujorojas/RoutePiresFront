package com.example.routepiresfront.data.model

import java.util.Date

data class AvaliacaoDTOCreate(
    val data: Date,
    val nota: Int,
    val comentario: String?,
    val corrida: String
)