package com.example.routepiresfront.data.model

import com.google.gson.annotations.SerializedName

data class MototaxistaCreate(
    @SerializedName("nome")
    val nome: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("telefone")
    val telefone: String,
    @SerializedName("senha")
    val senha: String,
    @SerializedName("fotoUrl")
    val fotoUrl: String? = null,
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