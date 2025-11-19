package com.example.routepiresfront.data.model

data class MototaxistaDTOProfile(
    val nome: String? = null,
    val disponivel: Boolean? = null,
    val servicosOferecidos: List<Servico>? = null,
    val avaliacaoMedia: Float? = null,
    val veiculo: VeiculoDTOProfile? = null
)