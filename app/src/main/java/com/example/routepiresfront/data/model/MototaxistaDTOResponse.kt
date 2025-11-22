package com.example.routepiresfront.data.model

data class MototaxistaDTOResponse(
    val id: String? = null,
    val nome: String? = null,
    val email: String? = null,
    val telefone: String? = null,
    // O campo 'apelido' foi removido pois não existe no DTO da API
    val fotoUrl: String? = null,
    val disponivel: Boolean? = null,
    val localizacaoAtual: Localizacao? = null,
    val veiculo: Veiculo? = null,
    // A API retorna uma lista de IDs/nome de serviços como strings — desserializamos como List<String>
    val servicosOferecidos: List<String>? = null,
    val avaliacoes: List<String>? = null, // Representação simplificada de DocumentReference
    val avaliacaoMedia: Float? = null,
    val cnh: String? = null,
    val veiculoDTO: VeiculoDTOResponse? = null
)
