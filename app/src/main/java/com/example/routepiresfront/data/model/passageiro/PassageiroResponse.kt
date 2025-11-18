package com.example.routepiresfront.data.model.passageiro

import java.util.Date

/**
 * DTO de resposta espelhando PassageiroResponseDTO do backend.
 * Todos os campos são opcionais para evitar crashes caso o backend mude algo.
 */
data class PassageiroResponse(
    val id: String? = null,
    val nome: String? = null,
    val telefone: String? = null,
    val dataCadastro: Date? = null,
    val tipo: String? = null,
    val fotoUrl: String? = null,
    val historicoCorridas: List<String>? = null,
    val metodoPagamentoPreferido: Pagamento? = null,
    val avaliacaoMedia: Float? = null,
    val avaliacoes: List<String>? = null
)
