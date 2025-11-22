package com.example.routepiresfront.data.model

data class UsuarioDTOResponse(
    val id: String,
    val nome: String,
    val email: String,
    val telefone: String,
    val dataCadastro: String, // Alterado de Date para String
    val tipo: String,
    val fotoUrl: String?,
    val historicoCorridas: List<String>? = null
)
