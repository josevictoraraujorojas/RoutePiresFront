package com.example.routepiresfront.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class ChatDTOResponse(
    val id: String?,
    val corrida: String?,
    val participantes: List<String>?,
    val mensagens: List<String>?,
    val dataCriacao: Date?,
    val dataEncerramento: Date?
): Parcelable
