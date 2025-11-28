package com.example.routepiresfront.data.model.auth

import java.util.Date

/**
 * Espelha UsuarioDTOResponse retornado no login.
 */
data class UsuarioResponse(
    val id: String? = null,
    val nome: String? = null,
    val email: String? = null,
    val telefone: String? = null,
    val dataCadastro: Date? = null,
    val tipo: String? = null,
    val fotoUrl: String? = null,
    val historicoCorridas: List<String>? = null
)
