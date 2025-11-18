package com.example.routepiresfront.data.model

import com.google.gson.annotations.SerializedName

data class MototaxistaCreate(
    @SerializedName("nomeCompleto")
    val nomeCompleto: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("telefone")
    val telefone: String,
    @SerializedName("senha")
    val senha: String,
    // CNH
    @SerializedName("cnh")
    val cnh: String,
    @SerializedName("disponivel")
    val disponivel: Boolean? = true,
    @SerializedName("localizacaoAtual")
    val localizacaoAtual: Localizacao? = null,
    @SerializedName("veiculo")
    val veiculo: VeiculoCreate,
    @SerializedName("servicosOferecidos")
    val servicosOferecidos: List<String>? = null
)