package com.example.routepiresfront.data.model

data class MototaxistaResponseDTO(
    val id: String? = null,
    val nome: String? = null,
    val email: String? = null,
    val telefone: String? = null,
    val dataCadastro: String? = null,
    val tipo: String? = null,
    val fotoUrl: String? = null,
    val historicoCorridas: List<String>? = null,
    val disponivel: Boolean? = null,
    val localizacaoAtual: Localizacao? = null,
    val servicosOferecidos: List<Servico>? = null,
    val avaliacoes: List<String>? = null,
    val avaliacaoMedia: Float? = null,
    val cnh: String? = null,
    val veiculo: VeiculoDTOResponse? = null
)