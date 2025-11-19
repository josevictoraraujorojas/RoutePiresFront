package com.example.routepiresfront.data.model

data class PassageiroResponseDTO(
    val id: String? = null,
    val nome: String? = null,
    val telefone: String? = null,
    val dataCadastro: String? = null, // ISO 8601 format
    val tipo: String? = null, // "PASSAGEIRO"
    val fotoUrl: String? = null,
    val historicoCorridas: List<String>? = null,
    val metodoPagamentoPreferido: String? = null,
    val avaliacaoMedia: Float? = null,
    val avaliacoes: List<String>? = null
)