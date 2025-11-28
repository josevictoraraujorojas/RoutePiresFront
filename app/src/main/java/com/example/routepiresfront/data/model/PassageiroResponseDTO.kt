package com.example.routepiresfront.data.model

data class PassageiroResponseDTO(
    val id: String? = null,
    val nome: String? = null,
    val email: String? = null,
    val telefone: String? = null,
    val dataCadastro: String? = null, // Retrofit converte para String
    val tipo: String? = null, // Enum vem como string
    val fotoUrl: String? = null,
    val historicoCorridas: List<String>? = null,
    val metodoPagamentoPreferido: String? = null, // Enum ou String
    val avaliacaoMedia: Float? = null,
    val avaliacoes: List<String>? = null
)
