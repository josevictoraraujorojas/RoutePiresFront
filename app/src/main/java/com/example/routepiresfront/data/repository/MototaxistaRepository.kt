package com.example.routepiresfront.data.repository

import com.example.routepiresfront.data.model.MototaxistaCreate
import com.example.routepiresfront.data.remote.MototaxistaApi
import com.example.routepiresfront.data.remote.responses.MototaxistaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MototaxistaRepository(private val api: MototaxistaApi) {

    suspend fun cadastrar(m: MototaxistaCreate): Result<MototaxistaResponse> = withContext(
        Dispatchers.IO) {
        try {
            val resp: Response<MototaxistaResponse> = api.cadastrarMototaxista(m)
            if (resp.isSuccessful) {
                val body = resp.body()
                if (body != null) Result.success(body) else Result.failure(Exception("Resposta vazia"))
            } else {
                val msg = resp.errorBody()?.string() ?: "Erro ${resp.code()}"
                Result.failure(Exception(msg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun listarTodos(): Result<List<MototaxistaResponse>> = withContext(Dispatchers.IO) {
        try {
            val resp = api.listarTodos()
            if (resp.isSuccessful) Result.success(resp.body() ?: emptyList()) else Result.failure(Exception("Erro ${resp.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun buscarPorId(id: String): Result<MototaxistaResponse> = withContext(Dispatchers.IO) {
        try {
            val resp = api.buscarPorId(id)
            if (resp.isSuccessful) resp.body()?.let { Result.success(it) } ?: Result.failure(Exception("Resposta vazia"))
            else Result.failure(Exception("Erro ${resp.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}