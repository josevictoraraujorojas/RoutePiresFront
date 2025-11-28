package com.example.routepiresfront.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Representa uma conversa de negociacao exibida na lista.
 */
@Parcelize
data class Negociacao(
    val nome: String,
    val mensagem: String,
    val chat: ChatDTOResponse,
    val usarioLogado: String,
    val quantidadeNaoLida: Int = 0
): Parcelable