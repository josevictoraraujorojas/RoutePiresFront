package com.example.routepiresfront.data.model

import com.google.gson.annotations.SerializedName

data class Corrida(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("passageiro")
    val passageiro: Passageiro? = null,

    @SerializedName("mototaxista")
    val mototaxista: MototaxistaInfo? = null,

    @SerializedName("tipo")
    val tipo: String = "corrida", // "corrida" ou "entrega"

    @SerializedName("status")
    val status: StatusCorrida = StatusCorrida.AGUARDANDO,

    @SerializedName("pontoPartida")
    val pontoPartida: Localizacao? = null,

    @SerializedName("pontoDestino")
    val pontoDestino: Localizacao? = null,

    @SerializedName("pontosIntermediarios")
    val pontosIntermediarios: List<Localizacao>? = null,

    @SerializedName("valorEstimado")
    val valorEstimado: Double? = null,

    @SerializedName("distanciaKm")
    val distanciaKm: Double? = null,

    @SerializedName("tempoEstimadoMin")
    val tempoEstimadoMin: Int? = null,

    @SerializedName("dataHoraCriacao")
    val dataHoraCriacao: String? = null,

    @SerializedName("dataHoraInicio")
    val dataHoraInicio: String? = null,

    @SerializedName("dataHoraFim")
    val dataHoraFim: String? = null,

    // Campos específicos para entrega
    @SerializedName("descricaoEntrega")
    val descricaoEntrega: String? = null,

    @SerializedName("pesoKg")
    val pesoKg: Int? = null,

    @SerializedName("fragil")
    val fragil: Boolean? = null
)

data class Passageiro(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("nome")
    val nome: String = "",

    @SerializedName("avaliacao")
    val avaliacao: Float = 0f,

    @SerializedName("fotoUrl")
    val fotoUrl: String? = null,

    @SerializedName("telefone")
    val telefone: String? = null
)

data class MototaxistaInfo(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("nome")
    val nome: String = "",

    @SerializedName("avaliacao")
    val avaliacao: Float = 0f,

    @SerializedName("fotoUrl")
    val fotoUrl: String? = null
)

data class Localizacao(
    @SerializedName("latitude")
    val latitude: Double = 0.0,

    @SerializedName("longitude")
    val longitude: Double = 0.0,

    @SerializedName("endereco")
    val endereco: String? = null,

    @SerializedName("nome")
    val nome: String? = null
)

enum class StatusCorrida {
    @SerializedName("AGUARDANDO")
    AGUARDANDO,

    @SerializedName("NEGOCIACAO")
    NEGOCIACAO,

    @SerializedName("ACEITA")
    ACEITA,

    @SerializedName("EM_ANDAMENTO")
    EM_ANDAMENTO,

    @SerializedName("FINALIZADA")
    FINALIZADA,

    @SerializedName("CANCELADA")
    CANCELADA
}
