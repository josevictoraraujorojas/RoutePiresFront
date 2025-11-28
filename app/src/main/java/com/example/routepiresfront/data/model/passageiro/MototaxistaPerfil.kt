package com.example.routepiresfront.data.model.passageiro

/**
 * Dados exibidos quando o passageiro abre o pop-up de detalhes do mototaxista.
 */
data class MototaxistaPerfil(
    val nome: String? = null,
    val disponivel: Boolean? = null,
    val servicosOferecidos: List<Servico>? = null,
    val avaliacaoMedia: Float? = null,
    val veiculo: VeiculoPerfil? = null
)
