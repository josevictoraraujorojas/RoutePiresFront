package com.example.routepiresfront.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // Emulador Android → backend rodando no PC
    private const val BASE_URL = "http://10.0.2.2:8080/"

    // Se usar celular físico → use o IP da máquina:
    // private const val BASE_URL = "http://192.168.x.x:8080/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging) // loga requisições/respostas
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun loginService(): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    fun mototaxistaPerfilService(): MototaxistaPerfilService {
        return retrofit.create(MototaxistaPerfilService::class.java)
    }

    fun passageiroPerfilService(): PassageiroPerfilService {
        return retrofit.create(PassageiroPerfilService::class.java)
    }
}