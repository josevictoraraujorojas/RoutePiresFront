package com.example.routepiresfront.data.model

data class Mensagem(
    val texto: String,
    val hora: String,
    val tipo: Int
) {
    companion object {
        const val TIPO_ENVIADA = 0
        const val TIPO_RECEBIDA = 1
    }
}