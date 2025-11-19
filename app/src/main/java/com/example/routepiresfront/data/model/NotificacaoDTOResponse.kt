package com.example.routepiresfront.data.model

data class NotificacaoDTOResponse(
    val id: String? = null,
    val usuarioId: String? = null,
    val titulo: String? = null,
    val mensagem: String? = null,
    val dataCriacao: String? = null,
    val lida: Boolean? = null,
    val tipo: String? = null // "CORRIDA", "AVALIACAO", "MENSAGEM", etc
)