package com.example.routepiresfront.data.model.passageiro

/**
 * Estrutura simplificada para listagem na tela de escolha.
 */
data class MototaxistaResumo(
    val id: String? = null,
    val nome: String? = null,
    val telefone: String? = null,
    val disponivel: Boolean? = null,
    val avaliacaoMedia: Float? = null,
    val servicosOferecidos: List<Servico>? = null,
    val veiculo: VeiculoPerfil? = null
)
