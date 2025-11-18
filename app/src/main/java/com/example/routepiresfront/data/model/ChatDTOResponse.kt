package com.example.routepiresfront.data.model

import java.util.Date

data class ChatDTOResponse(
    val id: String?,
    val corrida: String?,
    val participantes: List<String>?,
    val mensagens: List<String>?,
    val dataCriacao: Date?,
    val dataEncerramento: Date?
)
