package com.example.routepiresfront.data.model.passageiro

/**
 * Payload de cadastro/atualização do passageiro.
 * Mantido "vazio" (valores nulos por padrão) para ser preenchido pela camada de UI.
 */
data class PassageiroCadastroRequest(
    val nome: String? = null,
    val email: String? = null,
    val telefone: String? = null,
    val senha: String? = null,
    val fotoUrl: String? = null,
    val metodoPagamentoPreferido: Pagamento? = null
)
