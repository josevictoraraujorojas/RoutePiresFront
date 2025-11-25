package com.example.routepiresfront.data.remote

import com.example.routepiresfront.BuildConfig
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ApiClient {

    // Base URL vem do BuildConfig, mas normalizamos "localhost/127.0.0.1" para 10.0.2.2 (acesso ao host via emulador)
    private val baseUrl: String by lazy { normalizarBaseUrl(BuildConfig.API_BASE_URL) }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging) // loga requisições/respostas
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .registerTypeAdapter(
            Date::class.java,
            JsonDeserializer { json, _, _ ->
                val valor = json.asString
                // Aceita múltiplos formatos (backend retornando dd-MM-yyyy'T'HH:mm:ss.SSSZ)
                val formatos = listOf(
                    "dd-MM-yyyy'T'HH:mm:ss.SSSZ",
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
                )
                formatos.firstNotNullOfOrNull { padrao ->
                    runCatching {
                        SimpleDateFormat(padrao, Locale.getDefault()).parse(valor)
                    }.getOrNull()
                } ?: throw IllegalArgumentException("Formato de data não suportado: $valor")
            }
        )
        .create()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // Expondo o service pronto para uso nos repositórios/ViewModels
    val service: ApiService by lazy { instance.create(ApiService::class.java) }

    private fun normalizarBaseUrl(raw: String?): String {
        val fallback = "http://10.0.2.2:8080/"
        val limpa = (raw?.takeIf { it.isNotBlank() } ?: fallback)
        val normalizada = when {
            limpa.contains("localhost", ignoreCase = true) -> limpa.replace("localhost", "10.0.2.2", ignoreCase = true)
            limpa.contains("127.0.0.1") -> limpa.replace("127.0.0.1", "10.0.2.2")
            else -> limpa
        }
        return if (normalizada.endsWith("/")) normalizada else "$normalizada/"
    }
}
