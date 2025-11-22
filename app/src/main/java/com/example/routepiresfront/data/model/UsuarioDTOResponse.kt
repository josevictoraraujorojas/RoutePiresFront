package com.example.routepiresfront.data.model

import java.util.Date

data class UsuarioDTOResponse(
    val id: String,
    val nome: String,
    val email: String,
    val telefone: String,
    val dataCadastro: Date,
    val tipo: String,
    val fotoUrl: String?,
    val historicoCorridas: List<String>
)
