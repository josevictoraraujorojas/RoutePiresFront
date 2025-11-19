package com.example.routepiresfront.data.model

data class MototaxistaDTOResponse(
    val id: String? = null,
    val nome: String? = null,
    val email: String? = null,
    val telefone: String? = null,
    val disponivel: Boolean? = null,
    val localizacaoAtual: Localizacao? = null,
    val veiculo: Veiculo? = null,
    val servicosOferecidos: List<Servico>? = null,
    val avaliacoes: List<String>? = null,
    val avaliacaoMedia: Float? = null,
    val cnh: String? = null,
    val veiculoDTO: VeiculoDTOResponse? = null
)