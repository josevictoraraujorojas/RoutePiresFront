package com.example.routepiresfront.repository

import com.example.routepiresfront.data.model.MototaxistaResponseDTO
import com.example.routepiresfront.data.model.PassageiroResponseDTO
import com.example.routepiresfront.data.remote.MototaxistaService
import com.example.routepiresfront.data.remote.PassageiroService
import retrofit2.Response

class UsuarioRepository(
    private val mototaxistaService: MototaxistaService,
    private val passageiroService: PassageiroService
) {

    /** Tenta primeiro Mototaxista → se não achar, tenta Passageiro */
    suspend fun getNomeById(id: String): String? {

        // 🔹 1. Tenta buscar mototaxista
        val motoRes: Response<MototaxistaResponseDTO> =
            mototaxistaService.getMototaxistaById(id)

        if (motoRes.isSuccessful) {
            return motoRes.body()?.nome
        }

        // 🔹 2. Se não for mototaxista, tenta passageiro
        val pasRes: Response<PassageiroResponseDTO> =
            passageiroService.getPassageiroById(id)

        if (pasRes.isSuccessful) {
            return pasRes.body()?.nome
        }

        return null
    }
}
