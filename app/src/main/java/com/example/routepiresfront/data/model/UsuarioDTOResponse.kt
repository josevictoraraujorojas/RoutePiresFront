package com.example.routepiresfront.data.model

data class UsuarioDTOResponse(
    val id: String? = null,
    val nome: String? = null,
    val email: String? = null,
    val telefone: String? = null,
    val dataCadastro: String? = null,
    val tipo: String? = null,
    val fotoUrl: String? = null,
    val historicoCorridas: List<String>? = null
)
