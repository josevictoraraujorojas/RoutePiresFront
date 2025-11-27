package com.example.routepiresfront.data.repository

import com.example.routepiresfront.data.model.MototaxistaCreate
import com.example.routepiresfront.data.model.MototaxistaUpdate
import com.example.routepiresfront.data.model.VeiculoCreate
import com.example.routepiresfront.data.remote.MototaxistaApi
import com.example.routepiresfront.data.remote.responses.MototaxistaResponse
import com.example.routepiresfront.data.remote.responses.VeiculoResponse
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

    suspend fun editar(id: String, dados: MototaxistaUpdate): Result<MototaxistaResponse> =
        withContext(Dispatchers.IO) {
            try {
                val resp = api.editar(id, dados)
                if (resp.isSuccessful) {
                    val body = resp.body()
                    if (body != null) Result.success(body)
                    else Result.failure(Exception("Resposta vazia"))
                } else {
                    val msg = resp.errorBody()?.string() ?: "Erro ${resp.code()}"
                    Result.failure(Exception(msg))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    suspend fun buscarVeiculo(id: String): Result<VeiculoResponse> =
        withContext(Dispatchers.IO) {
            try {
                val resp = api.getVeiculo(id)

                // logs úteis (vai aparecer no logcat pelo Log.d)
                val requestUrl = resp.raw().request.url.toString()
                val code = resp.code()
                android.util.Log.d("VEICULO_RPC", "GET $requestUrl -> code=$code")

                if (resp.isSuccessful) {
                    val body = resp.body()
                    if (body != null) {
                        android.util.Log.d("VEICULO_RPC", "body: $body")
                        return@withContext Result.success(body)
                    } else {
                        android.util.Log.w("VEICULO_RPC", "response successful mas body nulo")
                        return@withContext Result.failure(Exception("Resposta vazia do servidor"))
                    }
                } else {
                    // tenta ler corpo de erro
                    val err = try { resp.errorBody()?.string() } catch (e: Exception) { null }
                    android.util.Log.e("VEICULO_RPC", "erro HTTP $code; bodyErro=$err")
                    return@withContext Result.failure(Exception("HTTP $code: ${err ?: "sem corpo de erro"}"))
                }
            } catch (e: Exception) {
                android.util.Log.e("VEICULO_RPC", "excep: ${e.message}", e)
                return@withContext Result.failure(e)
            }
        }


    suspend fun atualizarVeiculo(id: String, v: VeiculoCreate): Result<VeiculoResponse> =
        withContext(Dispatchers.IO) {
            try {
                val resp = api.atualizarVeiculo(id, v)
                if (resp.isSuccessful)
                    resp.body()?.let { Result.success(it) }
                        ?: Result.failure(Exception("Resposta vazia"))
                else
                    Result.failure(Exception("Erro ${resp.code()}"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}