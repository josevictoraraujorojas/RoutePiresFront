package com.example.routepiresfront.data.model

data class MototaxistaUpdate(
    val nome: String? = null,
    val email: String? = null,
    val telefone: String? = null,
    val senha: String? = null,
    val veiculo: VeiculoCreate? = null
)