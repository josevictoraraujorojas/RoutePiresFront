package com.example.routepiresfront.repository

import br.gov.ifgoiano.routepiresfront.data.model.DenunciaDTOCreate
import br.gov.ifgoiano.routepiresfront.data.model.DenunciaDTOResponse
import br.gov.ifgoiano.routepiresfront.data.remote.DenunciaService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class DenunciaRepository(
    private val service: DenunciaService
) {

    suspend fun criarDenuncia(dto: DenunciaDTOCreate): Response<DenunciaDTOResponse> {
        return withContext(Dispatchers.IO) {
            service.criarDenuncia(dto)
        }
    }

    suspend fun buscarDenunciaPorId(id: String): Response<DenunciaDTOResponse> {
        return withContext(Dispatchers.IO) {
            service.buscarDenunciaPorId(id)
        }
    }

    suspend fun listarTodas(): Response<List<DenunciaDTOResponse>> {
        return withContext(Dispatchers.IO) {
            service.listarTodasDenuncias()
        }
    }

    suspend fun validarDenuncia(denunciaId: String, adminId: String): Response<DenunciaDTOResponse> {
        return withContext(Dispatchers.IO) {
            service.validarDenuncia(denunciaId, adminId)
        }
    }

    suspend fun rejeitarDenuncia(denunciaId: String, adminId: String): Response<DenunciaDTOResponse> {
        return withContext(Dispatchers.IO) {
            service.rejeitarDenuncia(denunciaId, adminId)
        }
    }
}