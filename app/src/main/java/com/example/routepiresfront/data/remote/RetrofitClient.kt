package com.example.routepiresfront.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Objeto singleton para fornecer uma instância configurada do Retrofit e do ApiService.
 * Configurações:
 * - Emulador Android: usa 10.0.2.2 (localhost da máquina host)
 * - Dispositivo físico: altere para o IP da sua máquina na rede local (ex: 192.168.x.x)
 */
object RetrofitClient {

    // Emulador Android → backend rodando no PC
    private const val BASE_URL = "http://10.0.2.2:8080/"

    // Se usar celular físico, descomente e use o IP da sua máquina:
    // private const val BASE_URL = "http://192.168.0.103:8080/"

    /**
     * HttpLoggingInterceptor para debug
     * BODY: loga todo conteúdo de requisições e respostas
     * Recomendado apenas em desenvolvimento
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * OkHttpClient configurado com:
     * - Logging interceptor (desenvolvimento)
     * - Timeouts adequados para conexões lentas
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Gson configurado para serialização/deserialização
     * setLenient(): permite JSON mais flexível
     */
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    /**
     * Instância do Retrofit (lazy initialized)
     */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /**
     * Método genérico para criar serviços Retrofit
     * Use este método em vez de criar novas instâncias
     */
    fun <T> getService(clazz: Class<T>): T = retrofit.create(clazz)
}
